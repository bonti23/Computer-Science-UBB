package ubb.scs.map.faptebune.domain.validation;

import ubb.scs.map.faptebune.domain.Nevoie;

public class NevoieValidation implements Validation<Nevoie> {
    @Override
    public void validate(Nevoie entity) {
        if (entity.getTitlu() == null)
            throw new ValidationException("Title is null");
        if (entity.getDescriere() == null)
            throw new ValidationException("Description is null");
        if (entity.getDeadline() == null)
            throw new ValidationException("Deadline is null");
        if(entity.getStatus()==null)
            throw new ValidationException("Status is null");
    }
}
