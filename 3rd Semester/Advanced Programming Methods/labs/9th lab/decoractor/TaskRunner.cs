namespace Seminar_10.decorator;

public interface ITaskRunner
{
        void ExecuteOneTask();
        void ExecuteAll();
        void AddTask(Task t);
        bool HasTask();
}