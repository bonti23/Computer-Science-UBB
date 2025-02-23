package ubb.scs.map.examen.repository;

import ubb.scs.map.examen.domain.Entity;

import java.util.Optional;


public interface Repository<ID, E extends Entity<ID>>{
        Optional<E> findOne(ID id);
        Iterable<E> findAll();
}

