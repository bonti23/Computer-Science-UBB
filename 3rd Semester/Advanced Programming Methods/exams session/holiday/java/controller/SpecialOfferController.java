package ubb.scs.map.vacante.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import ubb.scs.map.vacante.domain.Hotel;
import ubb.scs.map.vacante.domain.SpecialOffer;
import ubb.scs.map.vacante.service.Service;

import java.time.LocalDate;

public class SpecialOfferController {

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private ListView<String> offList;

    private Service service;
    private Hotel hotel;
    private ObservableList<String> offModel = FXCollections.observableArrayList();

    public void setService(Service service, Hotel hotel) {
        this.service = service;
        this.hotel = hotel;
    }

    @FXML
    public void initialize() {
        offList.setItems(offModel);
    }

    public void handleSearch(ActionEvent event) {
        if (startDate.getValue() == null || endDate.getValue() == null) {
            MessageAlert.showErrorMessage(null, "Please select both start and end dates.");
            return;
        }

        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();
        offModel.clear();

        // Verifică ofertele după filtrare
        service.getOffers().stream()
                .filter(offer -> {
                    boolean isDateValid = offer.getStart().isBefore(end) && offer.getEnd().isAfter(start);
                    boolean isHotelValid = (hotel.getID().intValue())==(offer.getIdHotel());


                    return isDateValid && isHotelValid;
                })
                .forEach(offer -> {
                    offModel.add(offer.toString());
                });

        offList.setItems(offModel); // Asigură-te că setăm corect itemele în ListView
    }


}
