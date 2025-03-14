package ubb.scs.map.zboruri.repository;

import ubb.scs.map.zboruri.domain.Ticket;
import ubb.scs.map.zboruri.domain.validation.Validation;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.sql.DriverManager.getConnection;

public class TicketDBRepository extends AbstractDBRepository<Long, Ticket>{
    public TicketDBRepository(String url, String username, String password, Validation<Ticket> validator) {
        super(url,username,password,validator);
    }
    @Override
    public Optional<Ticket> findOne(Long id) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"ticket\" WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                String username = resultSet.getString("username");
                Integer idFlight=resultSet.getInt("idflight");
                LocalDateTime purchaseTime = resultSet.getTimestamp("purchasetime").toLocalDateTime();
                Ticket ticket = new Ticket(username,idFlight,purchaseTime);
                ticket.setID(ID1);
                return Optional.of(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public Iterable<Ticket> findAll() {
        Set<Ticket> tickets = new HashSet<>();
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"ticket\"");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                String username = resultSet.getString("username");
                Integer idFlight=resultSet.getInt("idflight");
                LocalDateTime purchaseTime = resultSet.getTimestamp("purchasetime").toLocalDateTime();
                Ticket ticket = new Ticket(username,idFlight,purchaseTime);
                ticket.setID(ID1);
                tickets.add(ticket);
            }
            return tickets;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
    public void adauga(Ticket ticket) {
        String sql = "INSERT INTO \"ticket\" (username,idflight,purchasetime) values (?,?,?)";

        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, ticket.getUsername());
            ps.setInt(2, ticket.getIdFlight());
            ps.setTimestamp(3, Timestamp.valueOf(ticket.getPurchaseTime()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
