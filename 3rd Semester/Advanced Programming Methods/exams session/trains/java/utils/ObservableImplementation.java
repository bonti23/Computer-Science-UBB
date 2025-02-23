package ubb.scs.map.examen.utils;

import java.util.ArrayList;
import java.util.List;

public class ObservableImplementat implements Observable{
    List<Observer> lista=new ArrayList<>();
    @Override
    public void addObserver(Observer o) {
        lista.add(o);
    }
    @Override
    public void removeObserver(Observer o) {
        lista.remove(o);
    }
    @Override
    public void notifyObservers() {
        for(Observer obs : lista){
            obs.update();
        }
    }
}
