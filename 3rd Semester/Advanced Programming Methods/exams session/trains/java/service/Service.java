package ubb.scs.map.examen.service;

import ubb.scs.map.examen.domain.City;
import ubb.scs.map.examen.domain.TrainStation;
import ubb.scs.map.examen.repository.CityDBRepository;
import ubb.scs.map.examen.repository.TrainStationDBRepository;
import ubb.scs.map.examen.utils.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    private TrainStationDBRepository trainStationDBRepository;
    private CityDBRepository cityDBRepository;
    private List<Observer> observers = new ArrayList<>();
    private Map<String, Integer> cautariMap = new HashMap<>();

    public Service(TrainStationDBRepository trainStationDBRepository, CityDBRepository cityDBRepository) {
        this.trainStationDBRepository = trainStationDBRepository;
        this.cityDBRepository = cityDBRepository;
    }

    public List<City> getCities() {
        //return cityDBRepository.findAll();
        List<City> citiesList = new ArrayList<>();
        cityDBRepository.findAll().forEach(citiesList::add);
        return citiesList;
    }

    public List<TrainStation> getTrainStations() {
        List<TrainStation> stationsList = new ArrayList<>();
        trainStationDBRepository.findAll().forEach(stationsList::add);
        return stationsList;
    }

    public String getNameById(String idOras){
        for (City c : getCities()) {
            if (c.getID().equals(idOras))
                return c.getName();
        }
        return null;
    }

    public void addCautare(String idDeparture, String idDestination){
        String key = idDeparture + "-" + idDestination;
        if (cautariMap.containsKey(key))
            cautariMap.put(key, cautariMap.get(key) + 1);
        else
            cautariMap.put(key, 1);
        notifyObservers();
    }

    public Integer nrPersoane(String from,String to){
        String key = from + "-" + to;
        return cautariMap.getOrDefault(key, 0);
    }
    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

}
