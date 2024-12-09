namespace Seminar_10;

public class TaskContainerFactory:Factory
{

    public IContainer CreateContainer(Strategy strategy)
    {
        if (strategy == Strategy.Fifo)
        {
            return new QueueContainer();
        }
        return new StackContainer(10);
    }
}