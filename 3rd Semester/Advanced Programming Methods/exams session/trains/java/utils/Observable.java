package ubb.scs.map.examen.utils;

public interface Observable{
    void addObserver(Observer e);
    void removeObserver(Observer e);
    void notifyObservers();
}
