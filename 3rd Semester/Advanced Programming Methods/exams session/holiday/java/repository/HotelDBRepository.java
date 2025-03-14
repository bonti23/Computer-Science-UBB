package ubb.scs.map.vacante.repository;

import ubb.scs.map.vacante.domain.HotelType;
import ubb.scs.map.vacante.domain.Hotel;
import ubb.scs.map.vacante.domain.validation.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.sql.DriverManager.getConnection;

public class HotelDBRepository extends AbstractDBRepository<Long, Hotel>{
    public HotelDBRepository(String url, String username, String password, Validation<Hotel> validator) {
        super(url,username,password,validator);
    }
    @Override
    public Optional<Hotel> findOne(Long id) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"hotel\" WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                Integer location = resultSet.getInt("location");
                String name = resultSet.getString("name");
                Integer rooms = resultSet.getInt("rooms");
                Integer price = resultSet.getInt("price");
                HotelType type = HotelType.valueOf(resultSet.getString("type"));
                Hotel hotel = new Hotel(location, name, rooms, price, type);
                hotel.setID(ID1);
                return Optional.of(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Iterable<Hotel> findAll() {
        Set<Hotel> hotels = new HashSet<>();
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"hotel\"");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                Integer location = resultSet.getInt("location");
                String name = resultSet.getString("name");
                Integer rooms = resultSet.getInt("rooms");
                Integer price = resultSet.getInt("price");
                HotelType type = HotelType.valueOf(resultSet.getString("type"));
                Hotel hotel = new Hotel(location, name, rooms, price, type);
                hotel.setID(ID1);
                hotels.add(hotel);
            }
            return hotels;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }
}
