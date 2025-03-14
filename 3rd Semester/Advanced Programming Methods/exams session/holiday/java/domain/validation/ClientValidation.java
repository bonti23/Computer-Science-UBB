package ubb.scs.map.vacante.domain.validation;

import ubb.scs.map.vacante.domain.Client;

import static ubb.scs.map.vacante.domain.Hobby.*;

public class ClientValidation implements Validation<Client> {
    @Override
    public void validate(Client entity) {
        if (entity.getName().isEmpty())
            throw new ValidationException("It cannot be empty");
        if(entity.getFidelitygrade()<=0 || entity.getFidelitygrade()>100)
            throw new ValidationException("Fidelity grade is invalid!");
        if(entity.getAge()<1)
            throw new ValidationException("Age is invalid!");
        if(entity.getHobby()!=reading || entity.getHobby()!=music || entity.getHobby()!=hiking || entity.getHobby()!=walking || entity.getHobby()!=extrem_sports)
            throw new ValidationException("Hobby is invalid!");
    }
}
