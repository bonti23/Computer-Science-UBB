package bonti;

import bonti.model.Game;
import bonti.model.Purchase;
import bonti.model.User;

import java.util.List;


public interface IService {
    User login(String username, String password, IObserver observer)throws Exception;
    void logout(int id, IObserver client);
    void signup(String name, String username, String password);
    List<Game> showcaseGamesByType(String type);
    void addPurchase(Purchase purchase) throws Exception;
    Game updateSeats(Game game, int seats);
    void validatePassword(String password);
    List<Game> showGames();
    boolean findByUsername(String username);
    List<Purchase> findByClientOrderedBySeats(String client);
    List<Purchase> allPurchases();

}