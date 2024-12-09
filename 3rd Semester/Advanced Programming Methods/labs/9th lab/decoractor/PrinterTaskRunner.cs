namespace Seminar_10.decorator;
using System;
using System.Globalization;

public class PrinterTaskRunner : AbstractTaskRunner
{
    private static readonly string TimeFormat = "HH:mm";
    public PrinterTaskRunner(ITaskRunner taskRunner) : base(taskRunner) {
    }

    public override void ExecuteOneTask()
    {
        base.ExecuteOneTask();
        Console.WriteLine($"Task executed at: {DateTime.Now.ToString(TimeFormat, CultureInfo.InvariantCulture)}");
    }
}