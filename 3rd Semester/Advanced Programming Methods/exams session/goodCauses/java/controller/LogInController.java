package ubb.scs.map.faptebune.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ubb.scs.map.faptebune.domain.Persoana;
import ubb.scs.map.faptebune.service.NevoieService;
import ubb.scs.map.faptebune.service.PersoanaService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogInController {

    private PersoanaService persoanaService;
    private NevoieService nevoieService;
    private Stage logInStage;

    @FXML
    private final ObservableList<Persoana> persoaneModel = FXCollections.observableArrayList();

    @FXML
    private ListView<Persoana> listaPersoane;

    public void setPersoanaService(PersoanaService persoanaService) {
        this.persoanaService = persoanaService;
        System.out.println("PersoanaService a fost setat.");
        populateListView();  // Populatează lista după ce serviciul este setat
    }

    // Setează serviciul pentru nevoi
    public void setNevoieService(NevoieService nevoieService) {
        this.nevoieService = nevoieService;
        System.out.println("NevoieService a fost setat.");
    }
    public void setLogInStage(Stage logInStage) {
        this.logInStage = logInStage;
    }

    // Populatează ListView-ul cu datele din PersoanaService
    private void populateListView() {
        if (persoanaService != null) {
            List<Persoana> persoane = persoanaService.getAllPersoane();

            if (persoane != null && !persoane.isEmpty()) {
                persoaneModel.setAll(persoane);
                listaPersoane.setItems(persoaneModel);

                // Personalizează celulele pentru a afișa numele și prenumele persoanei
                listaPersoane.setCellFactory(param -> new ListCell<Persoana>() {
                    @Override
                    protected void updateItem(Persoana persoana, boolean empty) {
                        super.updateItem(persoana, empty);
                        if (empty || persoana == null) {
                            setText(null);
                        } else {
                            setText(persoana.getUsername());
                        }
                    }
                });
            } else {
                System.out.println("Nu au fost găsite persoane.");
            }
        } else {
            System.out.println("PersoanaService nu a fost setat.");
        }
    }

    @FXML
    public void initialize() {
        // Nu facem nimic în initialize() pentru că populateListView() este apelată atunci când serviciul este setat
        System.out.println("LogInController initialize");
    }



    // Setează lista de persoane în ListView
    public void setListaPersoane() {
        if (persoanaService != null) {
            List<Persoana> persoane = persoanaService.getAllPersoane();
            if (persoane == null || persoane.isEmpty()) {
                System.out.println("Nu au fost găsite persoane.");
            } else {
                System.out.println("Persoane găsite: " + persoane.size());
                for (Persoana p : persoane) {
                    System.out.println(p);  // Afișează persoanele pentru a verifica dacă sunt corecte
                }
            }
            persoaneModel.setAll(persoane);
            listaPersoane.setItems(persoaneModel);
        }
    }

    @FXML
    public void onSignUpButtonClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ubb/scs/map/faptebune/signup-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        Stage signUpStage = new Stage();
        signUpStage.setTitle("SignUp");
        signUpStage.setScene(scene);

        SignUpController signUpController = fxmlLoader.getController();
        signUpController.setPersoanaService(persoanaService);
        signUpController.setLogInController(this);
        signUpController.setOrase();
        signUpController.setSignUpStage(signUpStage);
        signUpController.setLogInStage(logInStage);

        signUpStage.show();
    }

    @FXML
    public void onSelectedPerson() throws IOException {
        Persoana persoana = listaPersoane.getSelectionModel().getSelectedItem();
        if (persoana != null) {
            // Căutăm alt view (de exemplu, "tabs-view.fxml")
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ubb/scs/map/faptebune/tabs-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);


            Stage tabsStage = new Stage();
            tabsStage.setTitle("Tabs");
            tabsStage.setScene(scene);

            // Obținem controllerul pentru noul view
            TabsController tabsController = fxmlLoader.getController();
            tabsController.setNevoieService(nevoieService);
            tabsController.setTabsStage(tabsStage);
            tabsController.setPersoanaService(persoanaService);
            tabsController.setPersoanaLogata(persoana);
            tabsController.initialize();

            // Afișăm noua fereastră
            tabsStage.show();
        } else {
            System.out.println("Nu a fost selectată nicio persoană!");
        }
    }
}
