namespace Seminar_10;

public interface IContainer {
    Task Remove();
    void Add(Task task);
    int Size();
    bool IsEmpty();
}