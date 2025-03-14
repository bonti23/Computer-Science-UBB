package ubb.scs.map.faptebune.repository;

import ubb.scs.map.faptebune.domain.Oras;
import ubb.scs.map.faptebune.domain.Persoana;
import ubb.scs.map.faptebune.domain.validation.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.sql.DriverManager.getConnection;

public class PersoanaDBRepository extends AbstractDBRepository<Long, Persoana> {
    public PersoanaDBRepository(String url, String username, String password, Validation<Persoana> validator) {
        super(url,username,password,validator);
    }

    @Override
    public Optional<Persoana> findOne(Long id) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"persoane\" WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String username = resultSet.getString("username");
                String parola = resultSet.getString("parola");
                Oras oras = Oras.valueOf(resultSet.getString("oras"));
                String strada = resultSet.getString("strada");
                String numarStrada = resultSet.getString("numarstrada");
                String telefon = resultSet.getString("telefon");
                Persoana persoana = new Persoana(nume,prenume,username,parola,oras,strada,numarStrada,telefon);
                persoana.setID(ID1);
                return Optional.of(persoana);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Iterable<Persoana> findAll() {
        Set<Persoana> persoane = new HashSet<>();
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"persoane\"");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String username = resultSet.getString("username");
                String parola = resultSet.getString("parola");
                Oras oras = Oras.valueOf(resultSet.getString("oras"));
                String strada = resultSet.getString("strada");
                String numarStrada = resultSet.getString("numarstrada");
                String telefon = resultSet.getString("telefon");
                Persoana persoana = new Persoana(nume,prenume,username,parola,oras,strada,numarStrada,telefon);
                persoana.setID(ID1);
                persoane.add(persoana);
            }
            return persoane;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persoane;
    }
    public Persoana addPersoana(Persoana persoana) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("INSERT INTO persoane (id, nume, prenume, username, parola, oras, strada, numarstrada, telefon) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setLong(1, persoana.getID());
            statement.setString(2, persoana.getNume());
            statement.setString(3, persoana.getPrenume());
            statement.setString(4, persoana.getUsername());
            statement.setString(5, persoana.getParola());
            statement.setString(6, persoana.getOras().toString());
            statement.setString(7, persoana.getStrada());
            statement.setString(8, persoana.getNumarStrada());
            statement.setString(9, persoana.getTelefon());

            int rowNumber = statement.executeUpdate();
            if (rowNumber > 0) {
                return persoana;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean verifyIfAPersonExist(String username) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM \"persoane\" WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int number = resultSet.getInt(1);
                return number > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public long getMaxId() {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT MAX(id) FROM \"persoane\"");
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
