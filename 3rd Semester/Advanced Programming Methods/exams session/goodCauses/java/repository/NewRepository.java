package ubb.scs.map.faptebune.repository;

import ubb.scs.map.faptebune.domain.Entity;

import java.util.Optional;

public interface NewRepository<ID, E extends Entity<ID>>{
    /**
     *
     * @param id -the id of the entity to be returned
     * id must not be null
     * @return an {@code Optional} encapsulating the entity with the given id
     * @throws IllegalArgumentException
     * if id is null.
     */
    Optional<E> findOne(ID id);
    /**
     *
     * @return all entities
     */
    Iterable<E> findAll();
    /**
     *
     * @param entity
     * entity must be not null
     * @return an {@code Optional} - null if the entity was saved,
     * - the entity (id already exists)
     * @throws ValidationException
     * if the entity is not valid
     * @throws IllegalArgumentException
     * if the given entity is null. *
     */
}
