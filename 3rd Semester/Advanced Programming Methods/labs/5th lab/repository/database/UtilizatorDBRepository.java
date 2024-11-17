package ubb.scs.map.repository.database;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.domain.validators.UtilizatorValidator;
import ubb.scs.map.domain.validators.Validator;
import ubb.scs.map.repository.Repository;

import java.sql.*;
import java.util.*;

public class UtilizatorDBRepository implements Repository<Long, Utilizator> {
    UtilizatorValidator utilizatorValidator;
    public UtilizatorDBRepository(UtilizatorValidator utilizatorValidator) {
        this.utilizatorValidator = utilizatorValidator;
    }

    @Override
    public Optional<Utilizator> findOne(Long aLong){
        String query="SELECT * FROM utilizatori WHERE id=?";
        Utilizator utilizator=null;
        try(Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/utilizatori", "alexandrabontidean", "alexandramiha");
            PreparedStatement statement = connection.prepareStatement(query);){
                statement.setLong(1, aLong);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String firstName = resultSet.getString("prenume");
                    String lastName = resultSet.getString("nume");
                    utilizator = new Utilizator(firstName, lastName);
                    utilizator.setId(aLong);
                }
            }
        catch (SQLException e) {
                throw new RuntimeException(e);
        }
        return Optional.ofNullable(utilizator);
    }

    @Override
    public Iterable<Utilizator> findAll() {
        HashMap<Long, Utilizator> users = new HashMap<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/utilizatori", "alexandrabontidean", "alexandramiha");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM utilizatori");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nume = resultSet.getString("prenume");
                String prenume = resultSet.getString("nume");
                Utilizator utilizator = new Utilizator(nume, prenume);
                utilizator.setId(id);

                users.put(utilizator.getId(), utilizator);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users.values();
    }

    @Override
    public Optional<Utilizator> save(Utilizator entity) {
        if (entity == null) {
            throw new IllegalArgumentException("User can't be null!");
        }
        String query = "INSERT INTO utilizatori(\"id\", \"prenume\", \"nume\") VALUES (?,?,?)";

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/utilizatori", "alexandrabontidean", "alexandramiha");
             PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(entity);
    }

    @Override
    public Optional<Utilizator> delete(Long aLong) {
        String query = "DELETE FROM utilizatori WHERE \"id\" = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/utilizatori", "alexandrabontidean", "alexandramiha");
             PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setLong(1, aLong);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Utilizator userToDelete = null;
        for (Utilizator user : findAll()) {
            if (Objects.equals(user.getId(), aLong)) {
                userToDelete = user;
            }
        }
        return Optional.ofNullable(userToDelete);
    }

    @Override
    public Optional<Utilizator> update(Utilizator entity) {
        return Optional.empty();
    }
}
