package bonti.repository;

import bonti.domain.Order;
import bonti.domain.OrderStatus;
import bonti.domain.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.medicines WHERE o.terminal = :terminal")
    List<Order> findAllByTerminal(Terminal terminal);
    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.medicines")
    List<Order> findAllWithMedicines();

}
