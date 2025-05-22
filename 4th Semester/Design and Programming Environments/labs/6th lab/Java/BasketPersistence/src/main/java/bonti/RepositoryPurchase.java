package bonti;

import bonti.model.Purchase;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositoryPurchase extends JpaRepository<Purchase, Long> {
    List<Purchase> findByClientOrderBySeatsDesc(String client);
}
