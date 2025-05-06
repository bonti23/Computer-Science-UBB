package ro.mpp2024;

import java.util.List;
import java.util.Optional;
import ro.mpp2024.IObserver;


public interface IService {
    User login(String username, String password, IObserver observer)throws Exception;
    void logout(int id, IObserver client);
    void signup(String name, String username, String password);
    List<Purchase> find_by_client(String client);
    List<Game> showcase_games_by_type(String type);
    void add_purchase(Purchase purchase) throws Exception;
    Game update_seats(Game game, int seats);
    void validatePassword(String password);
    List<Game> show_games();
    boolean find_by_username(String username);
}
