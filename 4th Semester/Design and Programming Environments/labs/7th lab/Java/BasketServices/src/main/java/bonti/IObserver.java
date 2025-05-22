package bonti;

import bonti.model.Game;

public interface IObserver {
    void notifyBoughtSeats(Game game) throws Exception;
}
