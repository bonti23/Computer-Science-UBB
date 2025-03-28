namespace basket.repository;
using basket.domain;

public interface PurchaseRepository : Repository<long, Purchase> {
    List<Purchase> findByClientOrderedBySeats(String client);
}
