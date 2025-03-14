package ubb.scs.map.zboruri.domain.validation;

import ubb.scs.map.zboruri.domain.Flight;

public class FlightValidation implements Validation<Flight> {
    @Override
    public void validate(Flight entity) {
        if (entity.getFrom().isEmpty()) {
            throw new ValidationException("It cannot be empty");
        }
        if (entity.getTo().isEmpty()) {
            throw new ValidationException("It cannot be empty");
        }
        if(entity.getSeats()<0)
            throw new ValidationException("Number of seats cannot be negative");
    }
}
