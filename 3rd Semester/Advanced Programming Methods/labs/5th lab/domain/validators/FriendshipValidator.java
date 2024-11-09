package ubb.scs.map.domain.validators;

import ubb.scs.map.domain.Friendship;
import ubb.scs.map.domain.Utilizator;
import ubb.scs.map.repository.memory.InMemoryRepository;
import ubb.scs.map.repository.Repository;

import java.util.Optional;

public class FriendshipValidator implements Validator<Friendship> {

    private InMemoryRepository<Long, Utilizator> repo;

    public FriendshipValidator(InMemoryRepository<Long, Utilizator> repo) {
        this.repo = repo;
    }

    @Override
    public void validate(Friendship entity) throws ValidationException {

        //Utilizator u1 = repo.findOne(entity.getIdUser1());
        //Utilizator u2 = repo.findOne(entity.getIdUser2());

        Optional<Utilizator> u1 = repo.findOne(entity.getIdUser1());
        Optional<Utilizator> u2 = repo.findOne(entity.getIdUser2());


        if (entity.getIdUser1() == null || entity.getIdUser2() == null)
            throw new ValidationException("Id-ul n u poate fi null! ");
        if (u1 == null || u2 == null)
            throw new ValidationException("Id-ul nu exista! ");
    }
}
