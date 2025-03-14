package ubb.scs.map.vacante.repository;

import ubb.scs.map.vacante.domain.Client;
import ubb.scs.map.vacante.domain.Hobby;
import ubb.scs.map.vacante.domain.validation.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.sql.DriverManager.getConnection;

public class ClientDBRepository extends AbstractDBRepository<Long, Client> {
    public ClientDBRepository(String url, String username, String password, Validation<Client> validator) {
        super(url,username,password,validator);
    }
    @Override
    public Optional<Client> findOne(Long id) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"client\" WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Integer fidelityGrade = resultSet.getInt("fidelitygrade");
                Integer age = resultSet.getInt("age");
                Hobby hobby = Hobby.valueOf(resultSet.getString("hobby"));
                Client client = new Client(name, fidelityGrade, age, hobby);
                client.setID(ID1);
                return Optional.of(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Iterable<Client> findAll() {
        Set<Client> clients = new HashSet<>();
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"client\"");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Integer fidelityGrade = resultSet.getInt("fidelitygrade");
                Integer age = resultSet.getInt("age");
                Hobby hobby = Hobby.valueOf(resultSet.getString("hobby"));
                Client client = new Client(name, fidelityGrade, age, hobby);
                client.setID(ID1);
                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}

