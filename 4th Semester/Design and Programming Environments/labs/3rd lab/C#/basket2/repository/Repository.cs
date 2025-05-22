namespace basket2.repository;
using basket2.domain;

public interface Repository<ID, E> where E : Entity<ID>
{
    void Save(E entity);
    void Update(E entity);
    void Delete(ID id);
    E findOne(ID id);
    IEnumerable<E> findAll();
}