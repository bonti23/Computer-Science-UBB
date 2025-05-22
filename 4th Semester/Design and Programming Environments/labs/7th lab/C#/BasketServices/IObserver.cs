namespace BasketServices;
using BasketModel;
public interface IObserver
{
    void NotifyBoughtSeats(Game game);
}