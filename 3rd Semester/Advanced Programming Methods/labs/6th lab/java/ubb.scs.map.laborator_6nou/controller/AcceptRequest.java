package ubb.scs.map.laborator_6nou.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ubb.scs.map.laborator_6nou.domain.FriendshipRequest;
import ubb.scs.map.laborator_6nou.domain.User;
import ubb.scs.map.laborator_6nou.service.FriendshipRequestService;
import ubb.scs.map.laborator_6nou.service.FriendshipService;
import ubb.scs.map.laborator_6nou.service.UserService;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AcceptRequest {
    @FXML
    public TableColumn<FriendshipRequest, String> statusColumn;
    @FXML
    public Label showMessage;
    @FXML
    private TableColumn<FriendshipRequest, String> firstnameColumn;
    @FXML
    private TableColumn<FriendshipRequest, String> lastnameColumn;
    @FXML
    private TableColumn<FriendshipRequest, String> dateColumn;
    @FXML
    public Button acceptButton;
    @FXML
    public Button rejectButton;
    @FXML
    public TableView tableView;

    private Long ID;
    private FriendshipRequestService friendshipRequestService;
    private FriendshipService friendshipService;
    private UserService userService;
    ObservableList<FriendshipRequest> model = FXCollections.observableArrayList();

    void setService(FriendshipRequestService friendshipRequestService, UserService userService, FriendshipService friendshipService, Long ID) {
        this.friendshipRequestService = friendshipRequestService;
        this.userService = userService;
        this.friendshipService = friendshipService;
        this.ID = ID;
        initModel();
    }

    private String getUserFirstName(Long userId) {
        User user = userService.findOne(userId);
        return user != null ? user.getFirstName() : "Unknown";
    }

    private String getUserLastName(Long userId) {
        User user = userService.findOne(userId);
        return user != null ? user.getLastName() : "Unknown";
    }


    public void initialize() {
        firstnameColumn.setCellValueFactory(cellData -> {
            Long senderId = cellData.getValue().getId_user1();
            User sender = userService.findOne(senderId);
            return new SimpleStringProperty(sender != null ? sender.getFirstName() : "Unknown");
        });

        lastnameColumn.setCellValueFactory(cellData -> {
            Long senderId = cellData.getValue().getId_user1();
            User sender = userService.findOne(senderId);
            return new SimpleStringProperty(sender != null ? sender.getLastName() : "Unknown");
        });

        dateColumn.setCellValueFactory(cellData -> {
            LocalDateTime timeSend = cellData.getValue().getTimeSend();
            if (timeSend != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                return new SimpleStringProperty(timeSend.format(formatter));
            } else {
                return new SimpleStringProperty("Unknown");
            }
        });

        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        tableView.setItems(model);
    }


    private void initModel() {
        Iterable<FriendshipRequest> messages = friendshipRequestService.findByReceiver(ID);
        List<FriendshipRequest> users = StreamSupport.stream(messages.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(users);
    }

    public void handleAccept(ActionEvent actionEvent) {
        FriendshipRequest request = (FriendshipRequest) tableView.getSelectionModel().getSelectedItem();
        //System.out.println(request.getSender() + " " + request.getReceiver());
        if (request != null){
            if(request.getStatus().equals("PENDING")){
                friendshipRequestService.acceptRequest(request.getSender(), request.getReceiver());
                //System.out.println("Inainte de add"+request.getSender() + " " + request.getReceiver());
                friendshipService.save(request.getSender(), request.getReceiver());
                showMessage.setText("You have a new friend.");
                showMessage.setVisible(true);
            }
            initModel();
        }
    }

    public void handleDecline(ActionEvent actionEvent) {
        FriendshipRequest request = (FriendshipRequest) tableView.getSelectionModel().getSelectedItem();
        System.out.println(request.getSender() + " " + request.getReceiver());
        if (request != null) {
            if (request.getStatus().equals("PENDING")) {
                friendshipRequestService.declineRequest(request.getSender(), request.getReceiver());
                showMessage.setText("You declined the friendship.");
                showMessage.setVisible(true);
            }
            initModel();
        }
    }

}



