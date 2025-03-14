package ubb.scs.map.zboruri.utils.observer;

public interface Observable{
    void addObserver(Observer e);

    void removeObserver(Observer e);

    void notifyObservers();
}
