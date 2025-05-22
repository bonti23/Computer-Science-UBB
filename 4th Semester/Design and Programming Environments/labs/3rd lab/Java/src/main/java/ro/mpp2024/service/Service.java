package ro.mpp2024.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.mpp2024.domain.Game;
import ro.mpp2024.domain.Purchase;
import ro.mpp2024.domain.User;
import ro.mpp2024.repository.*;

import ro.mpp2024.repository.RepositoryUser;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;



@Component
public class Service {
    private final RepositoryGame game_repo;
    private final RepositoryUser user_repo;
    private final RepositoryPurchase purchase_repo;

    @Autowired
    public Service(RepositoryGame game_repo, RepositoryUser user_repo, RepositoryPurchase purchase_repo) {
        this.game_repo = game_repo;
        this.user_repo = user_repo;
        this.purchase_repo = purchase_repo;
    }

    //loginul
    public Optional<User> login(String username, String password){
        for (User user : user_repo.findAll()){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
        //return user_repo.findAll().stream()
        //                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
        //                .findFirst();
    }

    public List<Purchase> find_by_client(String client){
        List<Purchase> purchases = new ArrayList<>();
        purchases=purchase_repo.findByClientOrderedBySeats(client);
        return purchases;
    }

    public void signup(String name, String username, String password){
        for(User user : user_repo.findAll()){
            if(user.getUsername().equals(username)){
                throw new IllegalArgumentException("Username already exists.");            }
        }
        validatePassword(password);

        //Optional<User> existingUser = user_repo.findAll().stream()
        //                .filter(user -> user.getUsername().equals(username))
        //                .findFirst();
        User newUser = new User(name, username, password);
        user_repo.save(newUser);
    }
    private void validatePassword(String password) {
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
    public List<Game> showcase_games_by_type(String type){
        List<Game> games = new ArrayList<>();
        games=game_repo.findByTypeOrderedByDate(type);
        return games;
    }
    public void add_purchase(Purchase purchase){
        Game game = game_repo.findOne(purchase.getGame()).orElseThrow(() -> new IllegalArgumentException("game not found."));
        update_seats(game, purchase.getSeats());
        purchase_repo.save(purchase);
    }
    public void update_seats(Game game, int seats){
        if(seats<=0)
            throw new IllegalArgumentException("invalid number of seats!");
        if(game.getSeats()<seats)
            throw new IllegalArgumentException("not enough number of seats!");
        game.setSeats(game.getSeats()-seats);
        game_repo.update(game);
    }
}
