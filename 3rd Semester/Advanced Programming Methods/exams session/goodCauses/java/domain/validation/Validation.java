package ubb.scs.map.faptebune.domain.validation;

public interface Validation<T>{
    void validate(T entity) throws ValidationException;
}
