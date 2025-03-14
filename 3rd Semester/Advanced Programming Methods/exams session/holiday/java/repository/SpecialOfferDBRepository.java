package ubb.scs.map.vacante.repository;

import ubb.scs.map.vacante.domain.SpecialOffer;
import ubb.scs.map.vacante.domain.validation.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.sql.DriverManager.getConnection;

public class SpecialOfferDBRepository extends AbstractDBRepository<Long, SpecialOffer> {
    public SpecialOfferDBRepository(String url, String username, String password, Validation<SpecialOffer> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Optional<SpecialOffer> findOne(Long id) {
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"specialoffer\" WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                Integer hotel = resultSet.getInt("hotel");
                LocalDate start = resultSet.getTimestamp("start").toLocalDateTime().toLocalDate();
                LocalDate end = resultSet.getTimestamp("end").toLocalDateTime().toLocalDate();
                Integer percent = resultSet.getInt("percent");
                SpecialOffer specialoffer = new SpecialOffer(hotel, start, end, percent);
                specialoffer.setID(ID1);
                return Optional.of(specialoffer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<SpecialOffer> findAll() {
        Set<SpecialOffer> specialoffers = new HashSet<>();
        try (Connection connection = getConnection(getUrl(), getUsername(), getPassword());
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"specialoffer\"");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long ID1 = resultSet.getLong("id");
                Integer hotel = resultSet.getInt("hotel");
                LocalDate start = resultSet.getTimestamp("start").toLocalDateTime().toLocalDate();
                LocalDate end = resultSet.getTimestamp("end").toLocalDateTime().toLocalDate();
                Integer percent = resultSet.getInt("percent");
                SpecialOffer specialoffer = new SpecialOffer(hotel, start, end, percent);
                specialoffer.setID(ID1);
                specialoffers.add(specialoffer);
            }
            return specialoffers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialoffers;
    }
}
