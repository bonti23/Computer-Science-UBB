package ubb.scs.map.vacante.domain.validation;

import ubb.scs.map.vacante.domain.SpecialOffer;

public class SpecialOfferValidation implements Validation<SpecialOffer>{
    @Override
    public void validate(SpecialOffer entity) {
        if(entity.getIdHotel()==null)
            throw new ValidationException("Hotel id is required");
        if(entity.getStart()==null)
            throw new ValidationException("Start date is required");
        if(entity.getEnd()==null)
            throw new ValidationException("End date is required");
        if(entity.getPercent()<=0 || entity.getPercent()>100)
            throw new ValidationException("Percentage must be between 1 and 100");
    }
}
