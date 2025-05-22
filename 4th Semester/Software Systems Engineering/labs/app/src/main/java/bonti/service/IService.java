package bonti.service;

import bonti.DTO.UserDTO;
import bonti.domain.Medicine;
import bonti.domain.Order;
import bonti.domain.Personnel;
import bonti.domain.Terminal;

import java.util.List;

public interface IService {
    void createAccount(String firstname, String lastname, String username, String password, String email, String phone, String role);
    UserDTO login(String username, String password);
    List<Medicine> getAllMedicines();
    List<Order> getOrdersForTerminal(Terminal terminal);
    void deleteOrder(long id);
    Personnel getPersonnelByUsername(String username);
    void addOrder(Order order);
    void updateOrder(Order order);
    List<Order> getAllOrders();
}
