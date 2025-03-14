package ubb.scs.map.vacante.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ubb.scs.map.vacante.domain.Client;
import ubb.scs.map.vacante.domain.Hotel;
import ubb.scs.map.vacante.domain.Location;
import ubb.scs.map.vacante.domain.SpecialOffer;
import ubb.scs.map.vacante.service.Service;
import javafx.event.ActionEvent;

import java.time.LocalDate;
import java.util.List;

public class ClientController {

    @FXML
    private TableView<SpecialOffer> tableView;
    @FXML
    private TableColumn<SpecialOffer, String> hotelColoana;
    @FXML
    private TableColumn<SpecialOffer, String> locatieColoana;
    @FXML
    private TableColumn<SpecialOffer, LocalDate> startdateColoana;
    @FXML
    private TableColumn<SpecialOffer, LocalDate> enddateColoana;

    @FXML
    private TableView<Hotel> hotelTable;
    @FXML
    private TableColumn<Hotel, String> numeHotelColoana;

    @FXML
    private DatePicker dataPicker;
    @FXML
    private TextField nrNoptiField;

    private Client client;
    private Service service;
    private Long idClient;

    private ObservableList<SpecialOffer> model = FXCollections.observableArrayList();
    private ObservableList<Hotel> modelHotel = FXCollections.observableArrayList();

    private SpecialOffer selectedOffer;

    public void setClient(Client client) {
        this.client = client;
        this.idClient = client.getID(); // Assuming Client has an `id` field
        initModel(); // Initialize data based on the client
    }

    public void setService(Service service, Long idClient) {
        this.service = service;
        this.idClient = idClient;
        initModel();
    }

    @FXML
    public void initialize() {
        tableView.setItems(model);

        startdateColoana.setCellValueFactory(new PropertyValueFactory<>("start"));
        enddateColoana.setCellValueFactory(new PropertyValueFactory<>("end"));

        hotelColoana.setCellValueFactory(c -> {
            SpecialOffer off = c.getValue();
            for (Hotel h : service.getHotels()) {
                if (off.getIdHotel().equals(h.getID().intValue())) {
                    return new ReadOnlyObjectWrapper<>(h.getName());
                }
            }
            return new ReadOnlyObjectWrapper<>("No Hotel");
        });

        locatieColoana.setCellValueFactory(c -> {
            SpecialOffer off = c.getValue();
            for (Hotel h : service.getHotels()) {
                if (off.getIdHotel().equals(h.getID().intValue())) {
                    Integer idLocatie = h.getLocation();
                    for (Location l : service.getLocations()) {
                        if (l.getID().intValue() == idLocatie) {
                            return new ReadOnlyObjectWrapper<>(l.getName());
                        }
                    }
                }
            }
            return new ReadOnlyObjectWrapper<>("No Location");
        });

        hotelTable.setItems(modelHotel);
        numeHotelColoana.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void initModel() {
        // Fetch available offers for the current client
        List<SpecialOffer> offers = service.getOffersAvailable(idClient);
        model.setAll(offers);

        // Fetch all hotels to display
        List<Hotel> hotels = service.getHotels();
        modelHotel.setAll(hotels);
    }

    public void handleRezerva(ActionEvent event) {
        // Verificăm dacă a fost selectat un hotel
        if (hotelTable.getSelectionModel().getSelectedItem() == null) {
            showErrorMessage("Please select a hotel.");
            return;
        }

        Hotel hotel = hotelTable.getSelectionModel().getSelectedItem();
        LocalDate start = dataPicker.getValue();
        if (start == null) {
            showErrorMessage("Please select a start date.");
            return;
        }

        // Validare pentru numărul de nopți
        Integer noNights = 0;
        try {
            noNights = Integer.parseInt(nrNoptiField.getText());
        } catch (NumberFormatException e) {
            showErrorMessage("Please enter a valid number of nights.");
            return;
        }

        // Verificăm dacă hotelul se află printre ofertele disponibile
        SpecialOffer validOffer = null;
        for (SpecialOffer offer : model) {
            if (offer.getIdHotel().equals(hotel.getID().intValue())) {
                validOffer = offer;
                break;
            }
        }

        if (validOffer == null) {
            showErrorMessage("The selected hotel is not part of the available offers.");
            return;
        }

        // Verificăm dacă numărul de nopți nu depășește perioada ofertei
        LocalDate offerStart = validOffer.getStart();
        LocalDate offerEnd = validOffer.getEnd();

        // Verificăm dacă perioada aleasă de client se încadrează în perioada ofertei
        if (start.isBefore(offerStart) || start.plusDays(noNights).isAfter(offerEnd)) {
            showErrorMessage("The selected dates are outside of the available offer period.");
            return;
        }

        // Verificăm dacă numărul de nopți nu depășește perioada ofertei
        if (noNights > offerEnd.minusDays(offerStart.toEpochDay()).getDayOfYear()) {
            showErrorMessage("The number of nights exceeds the available offer period.");
            return;
        }

        // Dacă toate validările sunt ok, adăugăm rezervarea
        service.adaugaRezervare(idClient, hotel.getID(), start, noNights);

        // Afișăm mesaj de confirmare
        showConfirmationMessage();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Metoda pentru a afișa un mesaj de confirmare
    private void showConfirmationMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reservation Confirmation");
        alert.setHeaderText("Reservation Successful");
        alert.setContentText("Your reservation has been made successfully.");
        alert.showAndWait();
    }

}
