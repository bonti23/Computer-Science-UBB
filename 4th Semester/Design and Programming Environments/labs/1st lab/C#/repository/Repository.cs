using basker.domain;

namespace basker.repository;

public interface Repository<ID, E> where E : Entity<ID>
{
    void Save(E entity);
    void Update(E entity);
    void Delete(ID id);
    E findOne(ID id);
    IEnumerable<E> findAll();
}
