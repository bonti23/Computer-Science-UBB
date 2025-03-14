package ubb.scs.map.vacante.domain.validation;

import ubb.scs.map.vacante.domain.Reservation;
public class ReservationValidation implements Validation<Reservation> {
    @Override
    public void validate(Reservation entity) {
        if(entity.getIdClient()==null)
            throw new ValidationException("Id client is required");
        if(entity.getIdHotel()==null)
            throw new ValidationException("Id client is required");
        if(entity.getNights()<=0)
            throw new ValidationException("Nights must be positive");
        if(entity.getDate()==null)
            throw new ValidationException("Date is required");
    }
}
