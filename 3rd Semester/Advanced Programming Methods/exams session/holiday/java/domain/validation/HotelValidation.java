package ubb.scs.map.vacante.domain.validation;

import ubb.scs.map.vacante.domain.Client;
import ubb.scs.map.vacante.domain.Hotel;

import static ubb.scs.map.vacante.domain.HotelType.*;

public class HotelValidation implements Validation<Hotel> {
    @Override
    public void validate(Hotel entity) {
        if(entity.getLocation() == null)
            throw new ValidationException("Location is required");
        if(entity.getName().isEmpty())
            throw new ValidationException("Name is required");
        if(entity.getPrice()<0)
            throw new ValidationException("Price must be positive");
        if(entity.getRooms() == null)
            throw new ValidationException("Rooms is required");
        if(entity.getType()!=family || entity.getType()!=teenagers || entity.getType()!=oldpeople)
            throw new ValidationException("Type is invalid");

    }

}
