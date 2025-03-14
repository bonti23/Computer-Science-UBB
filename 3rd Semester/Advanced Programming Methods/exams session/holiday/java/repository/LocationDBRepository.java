package ubb.scs.map.vacante.repository;

import ubb.scs.map.vacante.domain.Location;
import ubb.scs.map.vacante.domain.validation.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.sql.DriverManager.getConnection;

public class LocationDBRepository extends AbstractDBRepository<Long, Location>{
    public LocationDBRepository(String url, String username, String password, Validation<Location> validator) {
        super(url,username,password,validator);
    }
    @Override
    public Optional<Location> findOne(Long id) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"location\" WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Location location = new Location(name);
                location.setID(ID1);
                return Optional.of(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Iterable<Location> findAll() {
        Set<Location> locations = new HashSet<>();
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"location\"");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Location location = new Location(name);
                location.setID(ID1);
                locations.add(location);
            }
            return locations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }
}
