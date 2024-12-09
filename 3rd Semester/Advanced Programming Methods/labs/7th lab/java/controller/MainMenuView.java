package ubb.scs.map.laborator_6nou.controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ubb.scs.map.laborator_6nou.domain.Friendship;
import ubb.scs.map.laborator_6nou.domain.FriendshipRequest;
import ubb.scs.map.laborator_6nou.domain.User;
import ubb.scs.map.laborator_6nou.domain.event.FriendshipEntityChange;
import ubb.scs.map.laborator_6nou.service.FriendshipRequestService;
import ubb.scs.map.laborator_6nou.service.FriendshipService;
import ubb.scs.map.laborator_6nou.service.MessageService;
import ubb.scs.map.laborator_6nou.service.UserService;
import ubb.scs.map.laborator_6nou.utils.Observer;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainMenuView implements Observer<FriendshipEntityChange> {
    @FXML
    public TableColumn<User, String> firstnameColumn;
    @FXML
    public TableView tableView;
    @FXML
    public TableColumn<User, String> lastnameColumn;
    @FXML
    public Button sendRequestButton;
    @FXML
    public Button acceptRequestButton;
    @FXML
    public TableColumn<User, String> sinceColumn;
    @FXML
    public Button removeFriendButton;
    @FXML
    public Button removeUser;
    @FXML
    public Button accountSettingsButton;
    @FXML
    public Label userNameField;

    private Long IDUser;
    private UserService userService;
    private FriendshipService friendshipService;
    private FriendshipRequestService requestService;
    private Stage stage;
    private MessageService messageService;
    ObservableList<User> model = FXCollections.observableArrayList();

    public void setService(Long IDUser, UserService userService, FriendshipService friendshipService, FriendshipRequestService requestService, MessageService messageService, Stage stage) {
        this.IDUser = IDUser;
        this.userService = userService;
        this.friendshipService = friendshipService;
        this.requestService = requestService;
        this.messageService = messageService;  // Now messageService is initialized
        this.stage = stage;
        friendshipService.addObserver(this);
        initModel();
    }
    public void initialize() {
        firstnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));

        sinceColumn.setCellValueFactory(cellData -> {
            Long senderId = cellData.getValue().getID();
            Friendship sender = friendshipService.findOne(senderId, IDUser);
            return new SimpleStringProperty(sender != null ? sender.getDatesince().toString() : "Unknown");
        });

        tableView.setItems(model);
    }

    private void initModel() {
        Iterable<User> messages = friendshipService.getFriends(IDUser);
        List<User> users = StreamSupport.stream(messages.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(users);
        setUser(IDUser);
    }

    public void setUser(Long IDUser) {
        User user = userService.findOne(IDUser);
        String fullName=user.getFirstName()+" "+user.getLastName();
        userNameField.setText(fullName);
    }

    public void handleSendRequest(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ubb/scs/map/laborator_6nou/request-view.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        Scene scene = new Scene(root);
        Stage stage2 = new Stage();
        stage2.setScene(scene);
        stage2.setTitle("Instagram");

        RequestView requestView = loader.getController();
        requestView.setService(requestService,friendshipService, userService,IDUser);
        stage2.show();
    }

    public void handleAcceptRequest(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ubb/scs/map/laborator_6nou/accept-request.fxml"));

        AnchorPane root = (AnchorPane) loader.load();
        Scene scene = new Scene(root);
        Stage stage2 = new Stage();
        stage2.setScene(scene);
        stage2.setTitle("Instagram");

        AcceptRequest requestView = loader.getController();
        requestView.setService(requestService,userService,friendshipService,IDUser);
        stage2.show();

    }

    @Override
    public void update(FriendshipEntityChange friendshipEntityChange) {
        initModel();
    }

    public void handleRemoveFriend(ActionEvent actionEvent) {
        User request = (User) tableView.getSelectionModel().getSelectedItem();
        System.out.println(request.getID());
        Friendship friendship = friendshipService.findOne(request.getID(), IDUser);
        System.out.println(friendship.getID());
        FriendshipRequest friendshipRequest=requestService.findByIDs(IDUser,request.getID());
        System.out.println(friendshipRequest.getID());
        requestService.delete(friendshipRequest.getID());
        friendshipService.delete(friendship.getID());

    }

    public void handleRemoveUser(ActionEvent actionEvent) {
        User user = userService.findOne(IDUser);
        if (user != null) {
            Iterable<User> friends = friendshipService.getFriends(IDUser);
            for (User friend : friends) {
                Friendship friendship = friendshipService.findOne(friend.getID(), IDUser);
                if (friendship != null) {
                    friendshipService.delete(friendship.getID());
                }
            }
            Iterable<FriendshipRequest> requests = requestService.findAll();
            for (FriendshipRequest request : requests) {
                if (request.getSender().equals(IDUser) || request.getReceiver().equals(IDUser)) {
                    requestService.delete(request.getID());
                }
            }
            userService.delete(IDUser);
            System.out.println("User removed successfully!");
            stage.close();
        } else {
            System.out.println("User not found!");
        }
    }


    public void handleAccountSetting(ActionEvent actionEvent) {
        try {
            // Load the Account Settings view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ubb/scs/map/laborator_6nou/account-settings-view.fxml"));

            // Load the root layout from the FXML file
            AnchorPane root = loader.load();

            // Set up the scene and stage
            Scene scene = new Scene(root);
            Stage settingsStage = new Stage();
            settingsStage.setScene(scene);
            settingsStage.setTitle("Account Settings");

            // Get the controller and set up its services and user ID
            AccountSettings settingsController = loader.getController();
            settingsController.setService(userService, IDUser);

            // Show the settings stage
            settingsStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading the Account Settings view.");
        }
    }
    public void handleChatButtonClick(ActionEvent actionEvent) throws IOException {
        // Get the selected user from the table
        User selectedUser = (User) tableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            // Retrieve the ID and name of the selected user
            Long IdTo = selectedUser.getID();
            String receiverName = selectedUser.getFirstName() + " " + selectedUser.getLastName();

            // Load the chat view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ubb/scs/map/laborator_6nou/chat-view.fxml"));

            // Load the chat view
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage chatStage = new Stage();
            chatStage.setScene(scene);
            chatStage.setTitle("Chat");

            // Get the controller and pass necessary services and the user ID
            ChatView chatView = loader.getController();
            chatView.setService(messageService, userService, IdTo, IDUser, receiverName);

            // Show the chat window
            chatStage.show();
        } else {
            // Handle the case where no user is selected in the table
            System.out.println("No user selected.");
        }
    }


}