package ubb.scs.map.zboruri.domain.validation;

import ubb.scs.map.zboruri.domain.Client;

public class ClientValidation implements Validation<Client>{
    @Override
    public void validate(Client entity) {
        if (entity.getUsername().isEmpty()) {
            throw new ValidationException("It cannot be empty");
        }
        if(entity.getName().isEmpty()) {
            throw new ValidationException("It cannot be empty");
        }
    }
}
