package ubb.scs.map.examen.domain.validation;

import ubb.scs.map.examen.domain.City;

public class CityValidation implements Validation<City> {
    @Override
    public void validate(City entity) {
        if (entity.getName().isEmpty()) {
            throw new ValidationException("It cannot be empty");
        }
    }
}
