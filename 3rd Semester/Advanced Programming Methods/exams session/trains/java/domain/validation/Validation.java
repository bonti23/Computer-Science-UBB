package ubb.scs.map.examen.domain.validation;

import java.io.Serializable;

public interface Validation<T>{
    void validate(T entity) throws ValidationException;
}
