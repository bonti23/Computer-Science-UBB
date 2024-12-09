namespace Seminar_10;

public abstract class AbstractContainer : IContainer
{
    protected List<Task> tasks = new List<Task>();

    public abstract Task Remove();

    public abstract void Add(Task task);

    public int Size()
    {
        return tasks.Count;
    }

    public bool IsEmpty()
    {
        return tasks.Count == 0;
    }
}
