package ubb.scs.map.examen.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ubb.scs.map.examen.domain.City;
import ubb.scs.map.examen.domain.TrainStation;
import ubb.scs.map.examen.service.Service;
import ubb.scs.map.examen.utils.Observer;


import java.util.*;

public class ClientController implements Observer {
    private Service service;
    @FXML
    private ComboBox<String> departureCombo;
    @FXML
    private ComboBox<String> destinationCombo;

    @FXML
    private Button cautaButton;

    @FXML
    private CheckBox checkBox;
    @FXML
    private ListView<String> listView;
    @FXML
    private Label textCasuta;

    private String idOm;
    private Double constanta = 10.0;//pretul per statie

    private Double calcul(Integer nrStatii) {
        return constanta*nrStatii;
    }

    ObservableList<String> model = FXCollections.observableArrayList();

    @FXML
    public void setService(Service service) {
        this.service = service;
        initModel();
        service.addObserver(this);
        //this.idOm = UUID.randomUUID().toString();
    }

    @FXML
    public void initialize() {
        listView.setItems(model);
        updateCasuta();
    }

    private void initModel() {
        setCombo();
        updateCasuta();
    }

    private void updateCasuta() {
        try {
            if (departureCombo.getValue() != null && destinationCombo.getValue() != null) {
                String from = departureCombo.getValue();
                String to = destinationCombo.getValue();

                Integer cnt = service.nrPersoane(from, to);
                if (cnt==1)
                    textCasuta.setText("You are the only one who is looking for this route!");
                else {
                    if (cnt==2)
                        textCasuta.setText("Hurry! " +(cnt-1)+ " more person is looking for the same route as you!");
                    else
                        textCasuta.setText("Hurry! " +(cnt-1)+ " more people are looking for the same route as you!");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            textCasuta.setText("Error!");
        }
    }

    public void setCombo() {
        List<City> citiesList = service.getCities();

        citiesList.sort(Comparator.comparing(City::getID));

        Set<String> from = new HashSet<>();
        Set<String> to = new HashSet<>();
        for (City c : citiesList) {
            from.add(c.getName());
            to.add(c.getName());
        }

        departureCombo.getItems().clear();
        destinationCombo.getItems().clear();
        departureCombo.getItems().addAll(from);
        destinationCombo.getItems().addAll(to);
    }

    public void handleSearch() {
        if (departureCombo.getValue() != null && destinationCombo.getValue() != null) {
            String from = departureCombo.getValue();
            String to = destinationCombo.getValue();
            model.clear();
            if (checkBox.isSelected()) {
                findDirectRoutes(from, to);
            } else {
                List<String> route = new ArrayList<>();
                findRoutes(from, to, route);
            }
            service.addCautare(from, to);
            Integer nrPersoane = service.nrPersoane(from, to);
            if (nrPersoane == 0 || nrPersoane == 1) {
                textCasuta.setText("You are the only one who is looking at this route!");
            } else {
                if (nrPersoane == 2) {
                    textCasuta.setText("Hurry! " + (nrPersoane - 1) + " more person is looking at the same route as you!");
                } else {
                    textCasuta.setText("Hurry! " + (nrPersoane - 1) + " more people are looking at the same route as you!");
                }
            }
        }
    }


    private void findDirectRoutes(String from, String to) {
        boolean found = false;

        for (TrainStation ts : service.getTrainStations()) {
            String dep = service.getNameById(ts.getDepartureCityId());
            String dest = service.getNameById(ts.getDestinationCityId());

            if (dep.equals(from) && dest.equals(to)) {
                found = true;
                model.add(dep + " -> " + dest + " price: " + calcul(1).toString());
            }
        }

        if (!found) {
            showErrorMessage("Invalid direct route!");
        }
    }
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void findRoutes(String from, String to, List<String> route) {
        route.add(from);
        //boolean foundRoute = false;
        if (from.equals(to)) {
            model.add(String.join(" -> ", route) + " price: " + calcul(route.size() - 1).toString());
            //foundRoute = true;
        } else {
            for (TrainStation ts : service.getTrainStations()) {
                String depCity = service.getNameById(ts.getDepartureCityId());
                String destCity = service.getNameById(ts.getDestinationCityId());

                if (depCity.equals(from) && !route.contains(destCity)) {
                    findRoutes(destCity, to, route);
                    //foundRoute = true;
                }
            }
        }
        //if (!foundRoute)
            //showErrorMessage("Invalid route!");

        route.remove(route.size() - 1);
    }


    @Override
    public void update() {
        initModel();
    }
}
