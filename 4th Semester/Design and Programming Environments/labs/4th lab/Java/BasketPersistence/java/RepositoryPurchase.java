package ro.mpp2024;

import java.util.List;

public interface RepositoryPurchase extends Repository<Long, Purchase> {
    List<Purchase> findByClientOrderedBySeats(String client);
}
