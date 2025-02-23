package ubb.scs.map.examen.domain.validation;

import ubb.scs.map.examen.domain.TrainStation;

public class TrainStationValidation implements Validation<TrainStation> {
    @Override
    public void validate(TrainStation entity){
        if (entity.getDepartureCityId().isEmpty() || entity.getDestinationCityId().isEmpty()) {
            throw new ValidationException("It cannot be empty!");
        }
    }
}
