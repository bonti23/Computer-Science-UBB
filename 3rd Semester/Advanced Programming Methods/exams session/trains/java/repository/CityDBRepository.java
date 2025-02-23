package ubb.scs.map.examen.repository;

import ubb.scs.map.examen.domain.City;
import ubb.scs.map.examen.domain.validation.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.sql.DriverManager.getConnection;

public class CityDBRepository extends AbstractDBRepository<String, City> {
    public CityDBRepository(String url, String username, String password, Validation<City> validator) {
        super(url,username,password, validator);
    }
    @Override
    public Optional<City> findOne(String id) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"city\" WHERE id = ?");
            statement.setString('1', id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String ID1 = resultSet.getString("id");
                String name = resultSet.getString("nume");
                City city = new City(name);
                city.setID(ID1);
                return Optional.of(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Iterable<City> findAll() {
        Set<City> cities = new HashSet<>();
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"city\"");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                String ID1 = resultSet.getString("id");
                String name = resultSet.getString("nume");
                City city = new City(name);
                city.setID(ID1);
                cities.add(city);
            }
            return cities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
