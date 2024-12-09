namespace Seminar_10.decorator;

public class ContainerTaskRunner : ITaskRunner
{
    private readonly IContainer _container;

    public ContainerTaskRunner(IContainer container)
    {
        _container = container;
    }

    public void ExecuteOneTask()
    {
        if (!_container.IsEmpty())
        {
            var task = _container.Remove();
            task.Execute();
        }
    }

    public void ExecuteAll()
    {
        while (!_container.IsEmpty())
        {
            ExecuteOneTask();
        }
    }

    public void AddTask(Task t)
    {
        _container.Add(t);
    }

    public bool HasTask()
    {
        return !_container.IsEmpty();
    }
}