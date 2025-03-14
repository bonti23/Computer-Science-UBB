package ubb.scs.map.faptebune.repository;

import ubb.scs.map.faptebune.domain.Entity;
import ubb.scs.map.faptebune.domain.validation.Validation;

import java.util.Optional;

public abstract class AbstractDBRepository <ID,E extends Entity<ID>> implements NewRepository<ID,E> {
    private final String url;
    private final String username;
    private final String password;
    private final Validation<E> validator;

    public AbstractDBRepository(String url, String username, String password, Validation<E> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    public abstract Optional<E> findOne(ID id);

    public abstract Iterable<E> findAll();

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Validation<E> getValidator() {
        return validator;
    }
}
