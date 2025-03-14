package ubb.scs.map.zboruri.domain.validation;

public interface Validation<T>{
    void validate(T entity) throws ValidationException;
}
