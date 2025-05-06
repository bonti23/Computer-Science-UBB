namespace BasketPersistence;
using BasketModel;

public interface PurchaseRepository : Repository<long, Purchase> {
    List<Purchase> findByClientOrderedBySeats(String client);
}
