namespace Seminar_10.decorator;

public class StrategyTaskRunner: ITaskRunner{
    private readonly IContainer _container;

    public StrategyTaskRunner(Strategy strategy)
    {
        _container = new TaskContainerFactory().CreateContainer(strategy);
    }

    public void ExecuteOneTask()
    {
        var task = _container.Remove();
        task?.Execute();
    }

    public void ExecuteAll()
    {
        while (HasTask())
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