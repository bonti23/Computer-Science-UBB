package ubb.scs.map.examen.repository;

import ubb.scs.map.examen.domain.TrainStation;
import ubb.scs.map.examen.domain.validation.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.sql.DriverManager.getConnection;

public class TrainStationDBRepository extends AbstractDBRepository <String, TrainStation> {
    public TrainStationDBRepository(String url, String username, String password, Validation<TrainStation> validator) {
        super(url,username,password, validator);
    }
    @Override
    public Optional<TrainStation> findOne(String id) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"trainstation\" WHERE id = ?");
            statement.setString('1', id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String ID1 = resultSet.getString("id");
                String departureCityId = resultSet.getString("departurecityid");
                String destinationCityId = resultSet.getString("destinationcityid");
                TrainStation trainStation = new TrainStation(departureCityId, destinationCityId);
                trainStation.setID(ID1);
                return Optional.of(trainStation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Iterable<TrainStation> findAll() {
        Set<TrainStation> trainstations = new HashSet<>();
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"trainstation\"");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                String ID1 = resultSet.getString("id");
                String departureCityId = resultSet.getString("departurecityid");
                String destinationCityId = resultSet.getString("destinationcityid");
                TrainStation trainStation = new TrainStation(departureCityId, destinationCityId);
                trainStation.setID(ID1);
                trainstations.add(trainStation);
            }
            return trainstations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainstations;
    }
}
