package ubb.scs.map.zboruri.domain.validation;

import ubb.scs.map.zboruri.domain.Ticket;

public class TicketValidation implements Validation<Ticket> {
    @Override
    public void validate(Ticket entity) {
        if (entity.getUsername().isEmpty()) {
            throw new ValidationException("It cannot be empty");
        }
    }
}
