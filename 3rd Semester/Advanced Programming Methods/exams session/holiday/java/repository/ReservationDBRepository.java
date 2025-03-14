package ubb.scs.map.vacante.repository;

import ubb.scs.map.vacante.domain.Reservation;
import ubb.scs.map.vacante.domain.validation.Validation;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.sql.DriverManager.getConnection;

public class ReservationDBRepository extends AbstractDBRepository<Long, Reservation>{
    public ReservationDBRepository(String url, String username, String password, Validation<Reservation> validator) {
        super(url,username,password,validator);
    }
    @Override
    public Optional<Reservation> findOne(Long id) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"reservation\" WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                Integer client = resultSet.getInt("idclient");
                Integer hotel = resultSet.getInt("idhotel");
                LocalDate date = resultSet.getTimestamp("date").toLocalDateTime().toLocalDate();
                Integer nights = resultSet.getInt("nights");
                Reservation reservation = new Reservation(client, hotel, date, nights);
                reservation.setID(ID1);
                return Optional.of(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Iterable<Reservation> findAll() {
        Set<Reservation> reservations = new HashSet<>();
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"reservation\"");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                Integer client = resultSet.getInt("idclient");
                Integer hotel = resultSet.getInt("idhotel");
                LocalDate date = resultSet.getTimestamp("date").toLocalDateTime().toLocalDate();
                Integer nights = resultSet.getInt("nights");
                Reservation reservation = new Reservation(client, hotel, date, nights);
                reservation.setID(ID1);
                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
    public void adauga(Reservation reservation) {
        String sql = "INSERT INTO \"reservation\" (idclient,idhotel,date,nights) values (?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, reservation.getIdClient());
            ps.setInt(2, reservation.getIdHotel());
            ps.setTimestamp(3, Timestamp.valueOf(reservation.getDate().atStartOfDay()));
            ps.setInt(4, reservation.getNights());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
