namespace Seminar_10.decorator;
using System.Threading.Tasks;

public class DelayTaskRunner: AbstractTaskRunner
{
    public DelayTaskRunner(ITaskRunner taskRunner) : base(taskRunner){
    }

    public override void ExecuteAll()
    {
        while (HasTask())
        {
            ExecuteOneTask();
            Console.WriteLine("Waiting for 3 seconds...");
            try
            {
                Thread.Sleep(3000); 
            }
            catch (ThreadInterruptedException e)
            {
                Console.WriteLine(e);
            }
            Console.WriteLine($"Task executed at: {DateTime.Now.ToString("HH:mm:ss")}");
        }
        Console.WriteLine("All tasks have been executed.");
        }
}

