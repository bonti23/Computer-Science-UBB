namespace Seminar_10;

public interface Factory
{
    IContainer CreateContainer(Strategy strategy);

}
