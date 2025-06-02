package bonti;

import bonti.model.Game;
import bonti.model.Purchase;
import bonti.model.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BasketServicesRpcProxy implements IService {
    private final String host;
    private final int port;
    private IObserver client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private final BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    private final List<IObserver> observers = new CopyOnWriteArrayList<>();

    public BasketServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        this.qresponses = new LinkedBlockingDeque<>();
    }

    @Override
    public User login(String username, String password, IObserver client){
        initializeConnection();
        this.client=client;
        User user = new User("", username, password);
        Request req = new Request.Builder().type(RequestType.LOGIN).data(user).build();
        sendRequest(req);
        Response response = readResponse();

        if (response != null && response.type() != null && response.type() == ResponseType.LOGIN_SUCCESS) {
            return (User) response.data();
        } else if (response != null && response.type() != null && response.type() == ResponseType.LOGIN_FAILED) {
            closeConnection();
            return null;
        }
        return null;
    }


    @Override
    public void signup(String name, String username, String password) {
        initializeConnection();
        User user = new User(name, username, password);

        try {
            validatePassword(password);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }

        if (findByUsername(username)) {
            throw new IllegalArgumentException("Username already exists!");
        }

        Request req = new Request.Builder().type(RequestType.SIGNUP).data(user).build();
        sendRequest(req);

        Response response = readResponse();
        if (response != null && response.type() != null && response.type() == ResponseType.SIGNUP_FAILED) {
            System.out.println("Signup failed: " + response.data());
            throw new IllegalArgumentException("Signup failed: " + response.data());
        }
    }

    @Override
    public boolean findByUsername(String username) {
        initializeConnection();

        Request req = new Request.Builder()
                .type(RequestType.CHECK_USERNAME)
                .data(username)
                .build();
        sendRequest(req);
        Response response = readResponse();

        if (response != null && response.type() == ResponseType.USERNAME_EXISTS) {
            return true;
        }

        return false;
    }
    @Override
    public void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password is required");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        if (!Pattern.compile("[!@#$%^&]").matcher(password).find()) {
            throw new IllegalArgumentException("Password must contain at least one special character (!@#$%^&)");
        }
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter");
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            throw new IllegalArgumentException("Password must contain at least one digit");
        }
    }
    @Override
    public List<Game> showcaseGamesByType(String type) {
        if (connection == null || connection.isClosed()) {
            initializeConnection();
        }
        Request req = new Request.Builder().type(RequestType.FILTER_GAMES).data(type).build();
        sendRequest(req);
        Response response = readResponse();
        if (response != null && response.type() != null && response.type() == ResponseType.FILTERED_GAMES) {
            Object data = response.data();
            if (data instanceof List<?>) {
                List<?> list = (List<?>) data;
                if (!list.isEmpty() && list.get(0) instanceof Game) {
                    return (List<Game>) list;
                }
            }
            System.out.println("Error: data is not a List<Game>.");
            return new ArrayList<>();
        }
        else {
            System.out.println("Error filtering games: " + response.data());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Game> showGames(){
        if (connection == null || connection.isClosed()) {
            initializeConnection();
        }
        List<Game> games_final = showcaseGamesByType("FINAL");
        List<Game> games_semifinal = showcaseGamesByType("SEMIFINAL");
        List<Game> games_quarterfinal = showcaseGamesByType("QUARTERFINAL");
        List<Game> games_regular = showcaseGamesByType("REGULAR");
        List<Game> games_playoff = showcaseGamesByType("PLAYOFF");
        List<Game> all_games=new ArrayList<>();
        all_games.addAll(games_final);
        all_games.addAll(games_semifinal);
        all_games.addAll(games_quarterfinal);
        all_games.addAll(games_regular);
        all_games.addAll(games_playoff);
        List<Game> sortedGames = all_games.stream()
                .sorted(Comparator.comparing(Game::getDate))
                .collect(Collectors.toList());
        return sortedGames;
    }
    @Override
    public void addPurchase(Purchase purchase) {
        Request req = new Request.Builder().type(RequestType.ADD_PURCHASE).data(purchase).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.PURCHASE_FAILED) {
            System.out.println("Purchase failed: " + response.data());
        }
    }
    @Override
    public List<Purchase> findByClientOrderedBySeats(String client) {
        Request req = new Request.Builder().type(RequestType.FIND_BY_CLIENT).data(client).build();
        sendRequest(req);
        Response response = readResponse();

        if (response.type() == ResponseType.PURCHASES_FOUND) {
            return (List<Purchase>) response.data();
        } else {
            System.out.println("Error finding purchases: " + response.data());
            return new ArrayList<>(); // Return an empty list if error occurs
        }
    }

    public void logout(int id, IObserver client) {
        Request req = new Request.Builder().type(RequestType.LOGOUT).data(id).build();
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if (response.type() == ResponseType.ERROR) {
            System.out.println("Logout failed: " + response.data());
        }
    }

    private void initializeConnection() {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            System.err.println("Closing error: " + e.getMessage());
        }
    }

    private void sendRequest(Request request) {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            System.out.println("Error sending request: " + e.getMessage());
        }
    }

    private Response readResponse() {
        try {
            return qresponses.take();
        } catch (InterruptedException e) {
            System.out.println("Error reading response: " + e.getMessage());
            return null;
        }
    }

    private void startReader() {
        Thread reader = new Thread(new ReaderThread());
        reader.start();
    }

    private void handleUpdate(Response response) {
        try {
            if (response.type() == ResponseType.UPDATE) {
                Game game = (Game) response.data();
                System.out.println("Am primit o actualizare pentru jocul cu ID-ul: " + game.getId());

                client.notifyBoughtSeats(game);
            }
        } catch (Exception e) {
            System.err.println("Error while handling update: " + e.getMessage());
        }
    }

    private boolean isUpdate(Response response) {
        return response.type() == ResponseType.UPDATE;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("Received response: " + response);
                    if (isUpdate((Response) response)) {
                        handleUpdate((Response) response);
                    } else {
                        qresponses.put((Response) response);
                    }
                } catch (Exception e) {
                    System.err.println("Reader error: " + e.getMessage());
                }
            }
        }
    }
    @Override
    public Game updateSeats(Game game, int seats) {
        Request req = new Request.Builder()
                .type(RequestType.UPDATE_SEATS)
                .data(new Object[] {game, seats})
                .build();

        sendRequest(req);
        Response response = readResponse();

        if (response.type() == ResponseType.UPDATE_SEATS_SUCCESS) {
            return (Game) response.data();
        } else {
            System.out.println("Error updating seats: " + response.data());
            return game;
        }
    }
    @Override
    public List<Purchase> allPurchases() {
        Request req = new Request.Builder().type(RequestType.ALL_PURCHASES).build();
        sendRequest(req);
        Response response = readResponse();

        if (response != null && response.type() == ResponseType.PURCHASES_FOUND) {
            return (List<Purchase>) response.data();
        } else {
            System.out.println("Error retrieving all purchases: " + response.data());
            return new ArrayList<>();
        }
    }


}