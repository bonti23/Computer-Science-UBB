namespace basket2.repository;
using basket2.domain;

public interface PurchaseRepository : Repository<long, Purchase> {
    List<Purchase> findByClientOrderedBySeats(String client);
}