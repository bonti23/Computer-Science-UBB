package ubb.scs.map.vacante.domain.validation;

public interface Validation<T>{
    void validate(T entity) throws ValidationException;
}

