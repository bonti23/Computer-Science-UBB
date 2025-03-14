package ubb.scs.map.faptebune.repository;

import ubb.scs.map.faptebune.domain.Nevoie;
import ubb.scs.map.faptebune.domain.validation.Validation;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.sql.DriverManager.getConnection;

public class NevoieDBRepository extends AbstractDBRepository<Long, Nevoie> {
    public NevoieDBRepository(String url, String username, String password, Validation<Nevoie> validator) {
        super(url,username,password,validator);
    }
    @Override
    public Optional<Nevoie> findOne(Long id) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"nevoi\" WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                String titlu = resultSet.getString("titlu");
                String descriere = resultSet.getString("descriere");
                LocalDateTime deadline = resultSet.getTimestamp("deadline").toLocalDateTime();
                Long omInNevoie=resultSet.getLong("ominnevoie");
                Long omSalvator=resultSet.getLong("omsalvator");
                String status = resultSet.getString("status");
                Nevoie nevoie = new Nevoie(titlu,descriere,deadline,omInNevoie,omSalvator,status);
                nevoie.setID(ID1);
                return Optional.of(nevoie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Iterable<Nevoie> findAll() {
        Set<Nevoie> nevoi = new HashSet<>();
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"nevoi\"");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                String titlu = resultSet.getString("titlu");
                String descriere = resultSet.getString("descriere");
                LocalDateTime deadline = resultSet.getTimestamp("deadline").toLocalDateTime();
                Long omInNevoie=resultSet.getLong("ominnevoie");
                Long omSalvator=resultSet.getLong("omsalvator");
                String status = resultSet.getString("status");
                Nevoie nevoie = new Nevoie(titlu,descriere,deadline,omInNevoie,omSalvator,status);
                nevoie.setID(ID1);
                nevoi.add(nevoie);
            }
            return nevoi;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nevoi;
    }
    public long getMaxId() {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT MAX(id) FROM \"nevoi\"")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Nevoie updateNevoie(long idNevoie, long idPersoana) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE \"nevoi\" SET omsalvator = ?, status = ? WHERE id = ? AND status = ?")) {
            statement.setLong(1, idPersoana);
            statement.setString(2, "Erou gasit!");
            statement.setLong(3, idNevoie);
            statement.setString(4, "Caut erou!");

            int rowNumber = statement.executeUpdate();
            if (rowNumber > 0) {
                return new Nevoie("", "", LocalDateTime.now(), idNevoie, idPersoana, "Erou gasit!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Nevoie adaugaNevoie(Nevoie nevoie) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO \"nevoi\" (id, titlu, descriere, deadline, ominnevoie, omsalvator, status) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            statement.setLong(1, nevoie.getID());
            statement.setString(2, nevoie.getTitlu());
            statement.setString(3, nevoie.getDescriere());
            statement.setTimestamp(4, Timestamp.valueOf(nevoie.getDeadline()));
            statement.setLong(5, nevoie.getOmInNevoie());
            statement.setNull(6, Types.NULL);
            statement.setString(7, "Caut erou!");

            int rowNumber = statement.executeUpdate();
            if (rowNumber > 0) {
                return nevoie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
