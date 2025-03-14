package ubb.scs.map.vacante.domain.validation;

import ubb.scs.map.vacante.domain.Location;

public class LocationValidation implements Validation<Location> {
    @Override
    public void validate(Location entity) {
        if(entity.getName().isEmpty()) {
            throw new ValidationException("Name cannot be empty");
        }
    }
}
