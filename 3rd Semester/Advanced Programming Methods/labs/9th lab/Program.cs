// See https://aka.ms/new-console-template for more information

using Seminar_10.decorator;
//using Seminar_10.factory;
using Seminar_10.model;
using System;
using System.Linq;

namespace Seminar_10.model
{
    public class Seminar_10
    {
        public static void Main(string[] args)
        {
            var messageTask = new MessageTask("A", "Salut", DateTime.Now, "1", "Messaj");
            var messageTask2 = new MessageTask("B", "Salutare", DateTime.Now, "2", "Messaj2");
            var messageTask3 = new MessageTask("C", "Salutare", DateTime.Now, "3", "Messaj3");
            var messageTask4 = new MessageTask("D", "Salutare", DateTime.Now, "4", "Messaj4");
            var messageTask5 = new MessageTask("E", "Salutare", DateTime.Now, "5", "Messaj5");

            var factory = new TaskContainerFactory();
            var stackContainer = factory.CreateContainer(Strategy.Fifo); // Assuming Strategy.Fifo is correct

            stackContainer.Add(messageTask);
            stackContainer.Add(messageTask2);
            stackContainer.Add(messageTask3);
            stackContainer.Add(messageTask4);
            stackContainer.Add(messageTask5);

            var size = stackContainer.Size();
            Console.WriteLine($"Number of tasks in container: {size}");

            var containerTaskRunner = new ContainerTaskRunner(stackContainer);
            var delayTaskRunner = new DelayTaskRunner(containerTaskRunner);
            delayTaskRunner.ExecuteAll();
        }
    }
}
