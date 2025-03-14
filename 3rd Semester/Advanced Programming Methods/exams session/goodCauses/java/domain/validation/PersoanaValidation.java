package ubb.scs.map.faptebune.domain.validation;

import ubb.scs.map.faptebune.domain.Persoana;

import static ubb.scs.map.faptebune.domain.Oras.*;

public class PersoanaValidation implements Validation<Persoana> {
    @Override
    public void validate(Persoana entity) {
        if (entity.getNume() == null || entity.getNume().trim().isEmpty()) {
            throw new ValidationException("Name is empty!");
        }

        if (entity.getPrenume() == null || entity.getPrenume().trim().isEmpty()) {
            throw new ValidationException("First name is empty!");
        }

        if (entity.getUsername() == null || entity.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username is empty!");
        }

        if (entity.getParola() == null) {
            throw new ValidationException("Password is empty!");
        }

        if (entity.getOras()!=London || entity.getOras()!=Paris || entity.getOras()!=Nice || entity.getOras()!=Bedford) {
            throw new ValidationException("Invalid city!");
        }

        if (entity.getStrada() == null || entity.getStrada().trim().isEmpty()) {
            throw new ValidationException("Street is empty!");
        }

        if (entity.getNumarStrada() == null || entity.getNumarStrada().trim().isEmpty()) {
            throw new ValidationException("Number street is empty!");
        }

        if (entity.getTelefon() == null || !entity.getTelefon().matches("\\d{10}")) {
            throw new ValidationException("Phone number must contain 10 digits!");
        }
    }
}
