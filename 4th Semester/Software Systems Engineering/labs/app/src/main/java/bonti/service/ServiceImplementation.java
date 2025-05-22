package bonti.service;

import bonti.DTO.UserDTO;
import bonti.domain.*;
import bonti.repository.ChemistRepository;
import bonti.repository.MedicineRepository;
import bonti.repository.OrderRepository;
import bonti.repository.PersonnelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import java.util.List;
import org.hibernate.Hibernate;


@Service
@RequiredArgsConstructor
public class ServiceImplementation implements IService {
    private final ChemistRepository chemist_repo;
    private final PersonnelRepository personnel_repo;
    private final MedicineRepository medicine_repo;
    private final OrderRepository order_repo;

    @Override
    public void createAccount(String firstname, String lastname, String username, String password, String email, String phone, String role) {
        Role parsedRole= Role.valueOf(role.toUpperCase());
        boolean emailExists = chemist_repo.existsByEmail(email) || personnel_repo.existsByEmail(email);
        boolean usernameExists = chemist_repo.existsByUsername(username) || personnel_repo.existsByUsername(username);

        if (emailExists) {
            throw new IllegalArgumentException("Email already in use.");
        }
        if (usernameExists) {
            throw new IllegalArgumentException("Username already in use.");
        }
        if (parsedRole == Role.CHEMIST) {
            Chemist chemist = Chemist.builder()
                    .firstname(firstname)
                    .lastname(lastname)
                    .username(username)
                    .password(password)
                    .email(email)
                    .phone(phone)
                    .role(Role.CHEMIST)
                    .build();
            chemist_repo.save(chemist);
        } else if (parsedRole == Role.PERSONNEL) {
            Terminal randomTerminal = Terminal.values()[(int)(Math.random() * Terminal.values().length)];
            Section randomSection = Section.values()[(int)(Math.random() * Section.values().length)];

            Personnel personnel = Personnel.builder()
                    .firstName(firstname)
                    .lastName(lastname)
                    .username(username)
                    .password(password)
                    .email(email)
                    .phone(phone)
                    .terminal(randomTerminal)
                    .section(randomSection)
                    .role(Role.PERSONNEL)
                    .build();

            personnel_repo.save(personnel);
        } else {
            throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
    @Override
    public UserDTO login(String username, String password) {
        Chemist chemist = chemist_repo.findByUsername(username);
        if (chemist != null && chemist.getPassword().equals(password)) {
            return new UserDTO(username, Role.CHEMIST);
        }

        Personnel personnel = personnel_repo.findByUsername(username);
        if (personnel != null && personnel.getPassword().equals(password)) {
            return new UserDTO(username, Role.PERSONNEL);
        }

        return null;
    }
    @Override
    public List<Medicine> getAllMedicines() {
        return medicine_repo.findAll();
    }
    @Override
    public List<Order> getOrdersForTerminal(Terminal terminal) {
        return order_repo.findAllByTerminal(terminal);
    }


    @Override
    public void deleteOrder(long id) {
        order_repo.deleteById(id);
    }
    @Override
    public Personnel getPersonnelByUsername(String username) {
        return personnel_repo.findByUsername(username); // sau cum ai stocat datele
    }
    @Override
    public void addOrder(Order order) {
        order_repo.save(order);
    }
    @Override
    public void updateOrder(Order order) {
        order_repo.save(order);
    }
    @Override
    public List<Order> getAllOrders() {
        return order_repo.findAllWithMedicines();
    }



}

