package ubb.scs.map.zboruri.repository;

import ubb.scs.map.zboruri.domain.Flight;
import ubb.scs.map.zboruri.domain.validation.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.sql.DriverManager.getConnection;

public class FlightDBRepository extends AbstractDBRepository<Long, Flight>{
    public FlightDBRepository(String url, String username, String password, Validation<Flight> validator) {
        super(url,username,password,validator);
    }
    @Override
    public Optional<Flight> findOne(Long id) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"flight\" WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                String from = resultSet.getString("from");
                String to = resultSet.getString("to");
                LocalDateTime departureTime = resultSet.getTimestamp("departuretime").toLocalDateTime();
                LocalDateTime landingTime = resultSet.getTimestamp("landingtime").toLocalDateTime();
                Integer seats=resultSet.getInt("seats");
                Flight flight = new Flight(from, to, departureTime, landingTime, seats);
                flight.setID(ID1);
                return Optional.of(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Iterable<Flight> findAll() {
        Set<Flight> flights = new HashSet<>();
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"flight\"");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                String from = resultSet.getString("from");
                String to = resultSet.getString("to");
                LocalDateTime departureTime = resultSet.getTimestamp("departuretime").toLocalDateTime();
                LocalDateTime landingTime = resultSet.getTimestamp("landingtime").toLocalDateTime();
                Integer seats=resultSet.getInt("seats");
                Flight flight = new Flight(from, to, departureTime, landingTime, seats);
                flight.setID(ID1);
                flights.add(flight);
            }
            return flights;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }
}
