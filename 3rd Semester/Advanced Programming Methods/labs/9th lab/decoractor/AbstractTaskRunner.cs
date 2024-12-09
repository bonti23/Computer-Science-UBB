namespace Seminar_10.decorator;

public class AbstractTaskRunner : ITaskRunner {
    private readonly ITaskRunner _taskRunner;

    protected AbstractTaskRunner(ITaskRunner taskRunner){ 
        _taskRunner = taskRunner;
    }
    public virtual void ExecuteOneTask() { 
        _taskRunner.ExecuteOneTask();
    }
    public virtual void ExecuteAll(){ 
        while (HasTask()) {
                ExecuteOneTask();
        }
    }
    public virtual void AddTask(Task t) {
        _taskRunner.AddTask(t);
    }
    public virtual bool HasTask() {
        return _taskRunner.HasTask(); 
    }
}
