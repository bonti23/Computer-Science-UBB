package ubb.scs.map.repository.memory;

import ubb.scs.map.domain.Entity;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.domain.validators.ValidationException;
import ubb.scs.map.domain.validators.Validator;
import ubb.scs.map.repository.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    private Validator<E> validator;
    private final Map<ID, E> entities;
    private final String url;
    private final String user;
    private final String password;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        this.entities = new HashMap<>();
        this.url = "jdbc:postgresql://localhost:5432/utilizatori";
        this.user = "alexandrabontidean";
        this.password = "alexandramiha";
    }

    public InMemoryRepository(Validator<E> validator, String url, String user, String password) {
        this.validator = validator;
        this.entities = new HashMap<>();
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public Optional<E> findOne(ID id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public Optional<E> save(E entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        Optional<E> result = Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement("INSERT INTO utilizatori (id, prenume, nume) VALUES (?, ?, ?)")) {

            if (entity instanceof Utilizator) {
                Utilizator utilizator = (Utilizator) entity;
                statement.setLong(1, utilizator.getId());
                statement.setString(2, utilizator.getFirstName());
                statement.setString(3, utilizator.getLastName());
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<E> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("Entity can't be null!");
        }
        Optional<E> result = Optional.ofNullable(entities.remove(id));

        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement("DELETE FROM utilizatori WHERE id = ?")) {

            statement.setLong(1, (Long) id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;    }

    @Override
    public Optional<E> update(E entity) {

        if (entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);

        entities.put(entity.getId(), entity);

        if (entities.get(entity.getId()) != null) {
            entities.put(entity.getId(), entity);
            return Optional.empty();
        }
        return Optional.of(entity);

    }
}
