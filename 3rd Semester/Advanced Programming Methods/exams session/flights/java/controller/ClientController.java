package ubb.scs.map.zboruri.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import ubb.scs.map.zboruri.domain.Client;
import ubb.scs.map.zboruri.domain.Flight;
import ubb.scs.map.zboruri.domain.Ticket;
import ubb.scs.map.zboruri.service.Service;
import ubb.scs.map.zboruri.utils.observer.Observer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientController implements Observer {
    private Service service;
    private Client client;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label clientNameLabel;

    @FXML
    private TableColumn<Flight, LocalDateTime> departureColoana;

    @FXML
    private ComboBox<String> fromCombo;

    @FXML
    private TableColumn<Flight, LocalDateTime> landingColoana;

    @FXML
    private TableColumn<Flight, Integer> seatsColoana;

    @FXML
    private TableView<Flight> tableView;

    @FXML
    private ComboBox<String> toCombo;

    @FXML
    private Button cautaButton;
    @FXML
    private Button buyButton;
    @FXML
    private TableColumn<Flight,Integer> availableColoana;
    @FXML
    private Button nextButton;
    @FXML
    private Label messageLabel;

    private Integer index=5;
    ObservableList<Flight> model= FXCollections.observableArrayList();

    public void setService(Service service,Client client) {
        this.service = service;
        this.client=client;
        clientNameLabel.setText(client.getName());
        initModel();
        service.addObserver(this);
    }

    @FXML
    public void initialize() {
        tableView.setItems(model);
        departureColoana.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        departureColoana.setCellFactory(column -> new TableCell<Flight, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                }
            }
        });

        landingColoana.setCellValueFactory(new PropertyValueFactory<>("landingTime"));
        landingColoana.setCellFactory(column -> new TableCell<Flight, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                }
            }
        });
        seatsColoana.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("seats"));

        availableColoana.setCellValueFactory(c -> {
            Flight fl=c.getValue();
            Integer cnt=0;
            List<Ticket> tickets = service.getTickets();
            for(Ticket t: tickets)
            {
                if(t.getIdFlight().intValue()==fl.getID())
                    cnt++;
            }
            return new ReadOnlyObjectWrapper<>(fl.getSeats()-cnt);
        });

        tableView.getSortOrder().add(departureColoana);
        departureColoana.setSortType(TableColumn.SortType.ASCENDING);

        tableView.sort();

    }

    private void initModel() {
        setCombo();
        handleSearch();
    }

    public void setCombo(){
        fromCombo.getItems().clear();
        toCombo.getItems().clear();
        Set<String> from = new HashSet<>();
        Set<String> to = new HashSet<>();
        for(Flight flight : service.getFlights()) {
            from.add(flight.getFrom());
            to.add(flight.getTo());
        }
        fromCombo.getItems().addAll(from);
        toCombo.getItems().addAll(to);
    }

    public void handleSearch() {
        LocalDate start = datePicker.getValue();
        String from = fromCombo.getValue();
        String to = toCombo.getValue();

        if (start != null && from != null && to != null) {
            List<Flight> matchingFlights = service.getFlights().stream()
                    .filter(fl -> fl.getDepartureTime().toLocalDate().equals(start) &&
                            fl.getFrom().equals(from) &&
                            fl.getTo().equals(to))
                    .sorted((f1, f2) -> f1.getDepartureTime().compareTo(f2.getDepartureTime()))
                    .toList();

            int startIndex = Math.max(0, index - 5);
            int endIndex = Math.min(matchingFlights.size(), index);

            model.clear();
            model.addAll(matchingFlights.subList(startIndex, endIndex));
        }
    }

    public void handleBuy(ActionEvent event) {
        Flight flight = tableView.getSelectionModel().getSelectedItem();
        if (flight != null) {
            service.adaugaTicket(client.getUsername(), flight.getID().intValue());
            service.notifyObservers();
            handleSearch();
            tableView.refresh();
            messageLabel.setText(" ");
        } else {
            messageLabel.setText("Select a flight to buy a ticket.");
            //MessageAlert.showErrorMessage(null, "Select a flight to buy a ticket.");
        }
    }

    @Override
    public void update() {
        if (client != null && client.getName() != null) {
            clientNameLabel.setText(client.getName());
        } else {
            clientNameLabel.setText("Client name not available");
        }
        initModel();
    }

    public void handleNext(ActionEvent event){
        index=index+5;
        handleSearch();
    }

    public void handlePrevious(ActionEvent event){
        index=index-5;
        handleSearch();
    }

}
