namespace Seminar_10;

public class QueueContainer: IContainer
{
    private Queue<Task> _taskQueue;

    public QueueContainer()
    {
        _taskQueue = new Queue<Task>();
    }

    public void Add(Task task)
    {
        _taskQueue.Enqueue(task);
    }

    public Task Remove()
    {
        if (_taskQueue.Count == 0)
        {
            throw new InvalidOperationException("The container is empty.");
        }
        return _taskQueue.Dequeue();
    }

    public int Size()
    {
        return _taskQueue.Count;
    }

    public bool IsEmpty()
    {
        return _taskQueue.Count == 0;
    }
}
