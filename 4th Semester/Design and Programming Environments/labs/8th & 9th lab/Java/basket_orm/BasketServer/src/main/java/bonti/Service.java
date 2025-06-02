package bonti;

import bonti.model.Game;
import bonti.model.Purchase;
import bonti.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class Service implements IService {

    private final RepositoryGame gameRepository;
    private final RepositoryUser userRepository;
    private final RepositoryPurchase purchaseRepository;
    private final Map<Long, IObserver> loggedClients;

    private final int defaultThreadsNo = 4;

    private final List<IObserver> observers = new CopyOnWriteArrayList<>();
    @Autowired
    public Service(RepositoryGame gameRepository, RepositoryUser userRepository, RepositoryPurchase purchaseRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
        this.loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized User login(String username, String password, IObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer cannot be null!");
        }

        for (User user : userRepository.findAll()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                if (loggedClients.containsKey(user.getId())) {
                    throw new IllegalStateException("User already logged in");
                }

                loggedClients.put(user.getId(), observer);
                System.out.println("User [" + user.getId() + "] logged in successfully.");
                System.out.println("Observer registered for user [" + user.getId() + "]: " + observer);
                System.out.println("Currently logged clients: " + loggedClients.keySet());
                return user;
            }
        }

        throw new IllegalArgumentException("Invalid username or password");
    }


    @Override
    public void logout(int id, IObserver observer) {
        loggedClients.remove((long) id);
    }

    @Override
    public List<Purchase> findByClientOrderedBySeats(String client) {
        return purchaseRepository.findByClientOrderBySeatsDesc(client);
    }

    @Override
    public void signup(String name, String username, String password) {
        for (User user : userRepository.findAll()) {
            if (user.getUsername().equals(username)) {
                throw new IllegalArgumentException("Username already exists.");
            }
        }
        validatePassword(password);
        User newUser = new User(name, username, password);
        userRepository.save(newUser);
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

        return gameRepository.findByTypeOrderByDateAsc(type);
    }

    @Override
    public void addPurchase(Purchase purchase) throws Exception {
        Game game = gameRepository.findById(purchase.getGame())
                .orElseThrow(() -> new IllegalArgumentException("Game not found."));

        Game updatedGame = updateSeats(game, purchase.getSeats());
        purchase.setGame(updatedGame.getId());
        purchaseRepository.save(purchase);

        System.out.println("Notifying all clients about the seat purchase for game: " + updatedGame.getId());
        notifyBoughtSeats(updatedGame);
    }

    @Override
    public Game updateSeats(Game game, int seats) {
        if (seats <= 0) {
            throw new IllegalArgumentException("Invalid number of seats!");
        }
        if (game.getSeats() < seats) {
            throw new IllegalArgumentException("Not enough seats available!");
        }
        game.setSeats(game.getSeats() - seats);
        gameRepository.save(game);
        return game;
    }

    public synchronized void notifyBoughtSeats(Game game) {
        for (Map.Entry<Long, IObserver> entry : loggedClients.entrySet()) {
            Long id = entry.getKey();
            IObserver client = entry.getValue();
            try {
                System.out.println("Notifying client [" + id + "]");
                client.notifyBoughtSeats(game);
            } catch (Exception e) {
                System.err.println("Error notifying client " + id + ": " + e.getMessage());
            }
        }
    }


    @Override
    public boolean findByUsername(String username) {
        return userRepository.findAll().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    @Override
    public List<Game> showGames() {
        return StreamSupport.stream(gameRepository.findAll().spliterator(), false)
                .sorted(Comparator.comparing(Game::getDate))
                .collect(Collectors.toList());
    }
    @Override
    public List<Purchase> allPurchases(){
        return purchaseRepository.findAll();
    }
}
