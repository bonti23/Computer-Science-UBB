package ro.mpp2024;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

public class BasketClientRpcWorker implements Runnable, IObserver {
    private final IService server;
    private final Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public BasketClientRpcWorker(IService server, Socket connection){
        this.server = server;
        this.connection = connection;
        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e){
            e.printStackTrace();
            connected = false;
            closeConnection();
        }
    }
    private void closeConnection() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.err.println("Closing error: " + e);
        }
    }

    private Response handleRequest(Request request) {
        Response response = null;
        switch (request.type()) {
            case LOGIN: {
                System.out.println("Login request...");
                User user = (User) request.data();
                try {
                    User loggedIn = server.login(user.getUsername(), user.getPassword(), this);
                    return new Response.Builder()
                            .type(ResponseType.LOGIN_SUCCESS)
                            .data(loggedIn)
                            .build();
                } catch (Exception e) {
                    connected = false;
                    return new Response.Builder()
                            .type(ResponseType.LOGIN_FAILED)
                            .data(e.getMessage())
                            .build();
                }
            }

            case LOGOUT: {
                System.out.println("Logout request...");
                Integer id = (Integer) request.data();
                try {
                    server.logout(id, this);
                } catch (Exception e) {
                    System.err.println("Error during logout: " + e.getMessage());
                }
                connected = false;
                return new Response.Builder()
                        .type(ResponseType.LOGOUT_SUCCESS)
                        .build();
            }

            case SIGNUP: {
                System.out.println("Signup request...");
                User user = (User) request.data();
                try {
                    server.signup(user.getName(), user.getUsername(), user.getPassword());
                    return new Response.Builder()
                            .type(ResponseType.SIGNUP_SUCCESS)
                            .build();
                } catch (Exception e) {
                    return new Response.Builder()
                            .type(ResponseType.SIGNUP_FAILED)
                            .data(e.getMessage())
                            .build();
                }
            }

            case FILTER_GAMES: {
                System.out.println("Filter games by type...");
                String type = (String) request.data();
                try {
                    var games = server.showcase_games_by_type(type);
                    return new Response.Builder()
                            .type(ResponseType.FILTERED_GAMES)
                            .data(games)
                            .build();
                } catch (Exception e) {
                    return new Response.Builder()
                            .type(ResponseType.ERROR)
                            .data(e.getMessage())
                            .build();
                }
            }

            case ADD_PURCHASE: {
                System.out.println("Add purchase request...");
                Purchase purchase = (Purchase) request.data();
                try {
                    server.add_purchase(purchase);
                    return new Response.Builder()
                            .type(ResponseType.PURCHASE_SUCCESS)
                            .build();
                } catch (Exception e) {
                    return new Response.Builder()
                            .type(ResponseType.PURCHASE_FAILED)
                            .data(e.getMessage())
                            .build();
                }
            }
            case CHECK_USERNAME: {
                System.out.println("Check username request...");
                String username = (String) request.data();
                boolean exists = server.find_by_username(username);
                ResponseType type = exists ? ResponseType.USERNAME_EXISTS : ResponseType.USERNAME_NOT_FOUND;
                return new Response.Builder()
                        .type(type)
                        .build();
            }

            case UPDATE_SEATS: {
                System.out.println("Update seats request...");
                Object[] data = (Object[]) request.data();
                Game game = (Game) data[0];
                int seats = (int) data[1];
                try {
                    Game updatedGame = server.update_seats(game, seats);
                    return new Response.Builder()
                            .type(ResponseType.UPDATE_SEATS_SUCCESS)
                            .data(updatedGame)
                            .build();
                } catch (Exception e) {
                    return new Response.Builder()
                            .type(ResponseType.UPDATE_SEATS_FAILED)
                            .data(e.getMessage())
                            .build();
                }
            }

            case FIND_BY_CLIENT: {
                System.out.println("Find purchases by client request...");
                String client = (String) request.data();
                try {
                    var purchases = server.find_by_client(client);
                    return new Response.Builder()
                            .type(ResponseType.PURCHASES_FOUND)
                            .data(purchases)
                            .build();
                } catch (Exception e) {
                    return new Response.Builder()
                            .type(ResponseType.ERROR)
                            .data(e.getMessage())
                            .build();
                }
            }


            default:
                return new Response.Builder().type(ResponseType.ERROR).data("Unknown request type").build();
        }
    }

    private static final Response okResponse=new Response.Builder().type(ResponseType.OK).build();
    private static final Response errorResponse=new Response.Builder().type(ResponseType.ERROR).build();


    private void sendResponse(Response resp) throws IOException {
        if (output == null) {
            System.err.println("Output stream is null, cannot send response");
            return;
        }
        System.out.println("Sending response: " + resp);
        synchronized (output) {
            output.writeObject(resp);
            output.flush();
        }
    }


    @Override
    public void notifyBoughtSeats(Game game) throws Exception {
        System.out.println("Seats have been updated for game: " + game.get_identitykey());

        Response response = new Response.Builder()
                .type(ResponseType.UPDATE)
                .data(game)
                .build();
        try {
            sendResponse(response);
        } catch (IOException e) {
            throw new Exception("Failed to notify client about bought seats", e);
        }
    }
}
