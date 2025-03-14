package ubb.scs.map.vacante.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ubb.scs.map.vacante.HelloApplication;
import ubb.scs.map.vacante.domain.Hotel;
import ubb.scs.map.vacante.domain.Location;
import ubb.scs.map.vacante.service.Service;

public class MainController {

    @FXML
    private ComboBox<String> locations;
    @FXML
    private TableView<Hotel> hotelTable;
    @FXML
    private TableColumn<Hotel, String> numeColoana;
    @FXML
    private TableColumn<Hotel, String> typeColoana;
    @FXML
    private TableColumn<Hotel, Integer> pretColoana;
    @FXML
    private TableColumn<Hotel, Integer> roomsColoana;

    private Service service;
    private ObservableList<Hotel> hotelList = FXCollections.observableArrayList();
    private ObservableList<String> locationsList = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;
        initModel();
    }

    @FXML
    public void initialize() {
        locations.setItems(locationsList);
        numeColoana.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColoana.setCellValueFactory(new PropertyValueFactory<>("type"));
        roomsColoana.setCellValueFactory(new PropertyValueFactory<>("rooms"));
        pretColoana.setCellValueFactory(new PropertyValueFactory<>("price"));
        hotelTable.setItems(hotelList);
    }

    private void initModel() {
        locationsList.setAll(service.getLocations().stream().map(Location::getName).toList());
    }

    public void handleCombo(ActionEvent event) {
        String selectedLocation = locations.getSelectionModel().getSelectedItem();
        if (selectedLocation == null) return;

        Long locationId = service.getLocationId(selectedLocation);
        if (locationId == null) {
            MessageAlert.showErrorMessage(null, "Location not found.");
            return;
        }

        hotelList.setAll(service.getHotels().stream()
                .filter(hotel -> hotel.getLocation().equals(locationId.intValue()))
                .toList());
    }

    public void handleOff(ActionEvent event) {
        Hotel hotel = hotelTable.getSelectionModel().getSelectedItem();
        if (hotel == null) {
            MessageAlert.showErrorMessage(null, "Please select a hotel.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/ubb/scs/map/vacante/special-offer-view.fxml"));
            Parent root = loader.load();
            SpecialOfferController offController = loader.getController();
            offController.setService(service, hotel);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Special Offers");
            stage.show();
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }
}
