package ro.mpp2024;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class Service implements IService {
    private final RepositoryGame game_repo;
    private final RepositoryUser user_repo;
    private final RepositoryPurchase purchase_repo;
    private Map<Long,IObserver> loggedClients;
    private final int defaultThreadsNo=4;

    private final List<IObserver> observers = new CopyOnWriteArrayList<>();
    @Autowired
    public Service(RepositoryGame game_repo, RepositoryUser user_repo, RepositoryPurchase purchase_repo) {
        this.game_repo = game_repo;
        this.user_repo = user_repo;
        this.purchase_repo = purchase_repo;
        loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public User login(String username, String password, IObserver observer){
        for (User user : user_repo.findAll()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedClients.put(user.get_identitykey(), observer);
                return user;

            }
        }
        throw new IllegalArgumentException("Invalid username or password");
    }
    public synchronized void notifyBoughtSeats(Game game){
        System.err.println("I'M HERE");
        Iterable<Game> games = game_repo.findAll();
        ExecutorService executorService= Executors.newFixedThreadPool(this.defaultThreadsNo);
        loggedClients.forEach((id,client)-> {
            System.err.println("I'M IN FOR " + id);
            executorService.execute(()->{
                try{
                    System.err.println("Notifying ["+id+"]");
                    client.notifyBoughtSeats(game);
                }catch (Exception e){
                    System.err.println("error notifying game with ID: " + id + " Message: " + e.getMessage());
                }
            });
        });
        executorService.shutdown();
    }
    @Override
    public void logout(int id, IObserver client){
        IObserver localClient=loggedClients.remove(id);
        if (localClient != null) {
            observers.remove(client);
        }

    }
    @Override
    public List<Purchase> find_by_client(String client){
        return purchase_repo.findByClientOrderedBySeats(client);
    }

    @Override
    public void signup(String name, String username, String password) throws IllegalArgumentException {
        for(User user : user_repo.findAll()){
            if(user.getUsername().equals(username)){
                throw new IllegalArgumentException("Username already exists.");
            }
        }
        validatePassword(password);
        List<User> users = new ArrayList<>();
        user_repo.findAll().forEach(users::add);
        Long new_id = users.stream()
                .mapToLong(User::get_identitykey)
                .max()
                .orElse(0L) + 1;
        User newUser = new User(new_id, name, username, password);
        user_repo.save(newUser);
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        user_repo.findAll().forEach(users::add);
        return users;
    }

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
    public List<Game> showcase_games_by_type(String type){
        return game_repo.findByTypeOrderedByDate(type);
    }

    @Override
    public void add_purchase(Purchase purchase) throws Exception {
        Game game = game_repo.findOne(purchase.getGame())
                .orElseThrow(() -> new IllegalArgumentException("game not found."));

        Game updatedGame = update_seats(game, purchase.getSeats());
        purchase.setGame(updatedGame.get_identitykey());

        purchase_repo.save(purchase);
        System.out.println("Notificăm toți clienții despre achiziția de locuri pentru jocul: " + updatedGame.get_identitykey());

        notifyBoughtSeats(updatedGame);
    }

    @Override
    public Game update_seats(Game game, int seats){
        if(seats <= 0)
            throw new IllegalArgumentException("invalid number of seats!");
        if(game.getSeats() < seats)
            throw new IllegalArgumentException("not enough number of seats!");
        game.setSeats(game.getSeats() - seats);
        game_repo.update(game);
        return game;
    }

    public List<Purchase> findAllPurchases(){
        List<Purchase> purchases = new ArrayList<>();
        for (Purchase purchase : purchase_repo.findAll()) {
            purchases.add(purchase);
        }
        return purchases;
    }

    private void notifyAllObservers(Game game) throws Exception {
        for (IObserver observer : observers) {
            observer.notifyBoughtSeats(game);
        }
    }

    @Override
    public boolean find_by_username(String username) {
        Iterable<User> users = user_repo.findAll();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Game> all_games(){
        return (List<Game>) game_repo.findAll();
    }
    @Override
    public List<Game> show_games(){
        Iterable<Game> games = game_repo.findAll();
        List<Game> sortedGames = StreamSupport.stream(games.spliterator(), false)
                .sorted(Comparator.comparing(Game::getDate))
                .collect(Collectors.toList());
        return sortedGames;
    }
}
