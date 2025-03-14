using NBA_League.domain;

namespace NBA_League.repository;

public delegate E CreateEntity<E>(string line);

abstract class InFileRepository<ID, E> : InMemoryRepository<ID, E> where E : Entity<ID>
{
    protected string filename;
    protected CreateEntity<E> createEntity;
    public InFileRepository(string filename, CreateEntity<E> createEntity)
    {
        this.filename = filename;
        this.createEntity = createEntity;
        if (this.createEntity != null)
            this.LoadFromFile();
    }
    protected virtual void LoadFromFile()
    {
        List<E> list = DataReader.ReadData(this.filename, this.createEntity);
        list.ForEach(x=>entities[x.Id] = x);
    }
}
