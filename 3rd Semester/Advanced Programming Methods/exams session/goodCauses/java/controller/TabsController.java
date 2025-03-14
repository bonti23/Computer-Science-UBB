package ubb.scs.map.faptebune.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import ubb.scs.map.faptebune.HelloApplication;
import ubb.scs.map.faptebune.domain.Nevoie;
import ubb.scs.map.faptebune.domain.Persoana;
import ubb.scs.map.faptebune.service.NevoieService;
import ubb.scs.map.faptebune.service.PersoanaService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TabsController {

    private NevoieService nevoieService;
    private final ObservableList<Nevoie> fapteBuneModel = FXCollections.observableArrayList();
    private final ObservableList<Nevoie> nevoiModel = FXCollections.observableArrayList();
    private Persoana persoanaLogata;
    private PersoanaService persoanaService;
    private Stage tabsStage;

    @FXML
    private TabPane tabsPane;

    @FXML
    private TableView<Nevoie> fapteBuneTable;

    @FXML
    private TableColumn<Nevoie, String> titlu1;

    @FXML
    private TableColumn<Nevoie, String> descriere1;

    @FXML
    private TableColumn<Nevoie, LocalDateTime> deadline1;

    @FXML
    private TableColumn<Nevoie, Long> omInNevoie1;

    @FXML
    private TableColumn<Nevoie, Long> omSalvator1;

    @FXML
    private TableColumn<Nevoie, String> status1;

    @FXML
    private TableView<Nevoie> nevoiTable;

    @FXML
    private TableColumn<Nevoie, String> titlu;

    @FXML
    private TableColumn<Nevoie, String> descriere;

    @FXML
    private TableColumn<Nevoie, LocalDateTime> deadline;

    @FXML
    private TableColumn<Nevoie, Long> omInNevoie;

    @FXML
    private TableColumn<Nevoie, Long> omSalvator;

    @FXML
    private TableColumn<Nevoie, String> status;

    public void setNevoieService(NevoieService nevoieService){
        this.nevoieService = nevoieService;

    }
    public void setPersoanaLogata(Persoana persoanaLogata){
        this.persoanaLogata = persoanaLogata;
    }



    public void setPersoanaService(PersoanaService persoanaService) {
        this.persoanaService = persoanaService;
    }

    public void setTabsStage(Stage tabsStage) {
        this.tabsStage = tabsStage;
    }

    public void setNevoiTable() {
        System.out.println("Intra in setNevoiTable()"); // Debugging
        List<Nevoie> nevoi = nevoieService.getAllNevoi();
        System.out.println("Numar total de nevoi: " + nevoi.size());  // Debugging: Check the number of items

        if (nevoi == null || nevoi.isEmpty()) {
            System.out.println("No nevoi found in the service.");
            return;  // Exit if there are no items to display
        }

        List<Nevoie> nevoiRez = new ArrayList<>();
        for (Nevoie nevoie : nevoi) {
            Persoana persoanaInNevoie = persoanaService.findOnePersoana(nevoie.getOmInNevoie());

            // Debugging: Verificăm persoana asociată
            if (persoanaInNevoie != null) {
                System.out.println("Found person with ID: " + persoanaInNevoie.getID());
            }

            // Aplicăm filtrarea (verificăm dacă persoana logată nu este în nevoie și dacă persoana este din același oraș)
            if (nevoie.getOmInNevoie() != persoanaLogata.getID() && persoanaInNevoie.getOras().toString().equals(persoanaLogata.getOras().toString())) {
                nevoiRez.add(nevoie);
            }
        }

        if (nevoiRez.isEmpty()) {
            System.out.println("No filtered nevoi to display.");
        }

        // Actualizăm modelul de date al tabelului
        nevoiModel.setAll(nevoiRez);  // Update the table model with the data
        System.out.println("Nevoi model populated: " + nevoiModel.size());
    }
    @FXML
    public void setFapteBuneTable(){
        List<Nevoie> nevoi = nevoieService.getAllNevoi();
        System.out.println("Numar total de nevoi pentru fapte bune: " + nevoi.size());  // Debug: Verifică câte nevoi sunt returnate
        List<Nevoie> fapteBune = new ArrayList<>();

        for (Nevoie nevoie : nevoi) {
            // Verifică dacă persoana logată este salvatorul nevoii
            if (nevoie.getStatus().equals("Erou gasit!") && persoanaLogata.getID().equals(nevoie.getOmSalvator())) {
                fapteBune.add(nevoie);
            }
        }

        // Setează lista de fapte bune în modelul de date (ObservableList)
        fapteBuneModel.setAll(fapteBune);
    }

    @FXML
    public void initialize(){
        if (nevoieService == null) {
            System.out.println("NevoieService nu a fost setat! mesj afisat din initialize TabsController");
        }
        try {
            // Setarea coloanelor pentru tabelul "nevoi"
            titlu.setCellValueFactory(new PropertyValueFactory<>("titlu"));
            descriere.setCellValueFactory(new PropertyValueFactory<>("descriere"));
            deadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
            omInNevoie.setCellValueFactory(new PropertyValueFactory<>("omInNevoie"));
            omSalvator.setCellValueFactory(new PropertyValueFactory<>("omSalvator"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));

            // Setarea coloanelor pentru tabelul "fapte bune"
            titlu1.setCellValueFactory(new PropertyValueFactory<>("titlu"));
            descriere1.setCellValueFactory(new PropertyValueFactory<>("descriere"));
            deadline1.setCellValueFactory(new PropertyValueFactory<>("deadline"));
            omInNevoie1.setCellValueFactory(new PropertyValueFactory<>("omInNevoie"));
            omSalvator1.setCellValueFactory(new PropertyValueFactory<>("omSalvator"));
            status1.setCellValueFactory(new PropertyValueFactory<>("status"));

            nevoiTable.setItems(nevoiModel);
            fapteBuneTable.setItems(fapteBuneModel);
            setNevoiTable();

            updateTables();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> updateTables()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

        } catch (Exception e) {
            System.out.println("Error during initialization: " + e.getMessage());
        }
    }

    private void updateTables() {
        setNevoiTable();
        setFapteBuneTable();
    }

    @FXML
    public void onFapteBuneTableClicked() throws IOException {
        // Aici poți adăuga logica pentru când se face click pe tabelul de "fapte bune"
    }

    @FXML
    public void onTabsPaneClicked() throws IOException {
        String ajutor = tabsPane.getSelectionModel().getSelectedItem().getText();

        if(ajutor.equals("i need help")){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/ubb/scs/map/faptebune/nevoie-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

            Stage nevoieStage = new Stage();

            nevoieStage.setTitle("Nevoie");
            nevoieStage.setScene(scene);

            NevoieController nevoieController = fxmlLoader.getController();
            nevoieController.setPersoanaLogata(persoanaLogata);
            nevoieController.setNevoieService(nevoieService);
            nevoieController.setNevoieStage(nevoieStage);

            nevoieStage.show();
        }
    }

    @FXML
    public void onNevoiTableClicked() {
        // Get the selected item from the table
        Nevoie nevoieSelectata = nevoiTable.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (nevoieSelectata != null) {
            // Continue with the logic if an item is selected
            Nevoie nevoie = nevoieService.updateNevoie(nevoieSelectata.getID(), persoanaLogata.getID());

            if (nevoie != null) {
                setNevoiTable();

                Alert message = new Alert(Alert.AlertType.CONFIRMATION);
                message.initOwner(tabsStage);
                message.setTitle("Sarcina atribuita!");
                message.setContentText("V-ati asumat responsabilitatea rezolvarii unei nevoi!!!");
                message.showAndWait();
            } else {
                System.out.println("Nevoia nu poate fi actualizata!");
            }
        } else {
            // Handle the case where no item is selected
            Alert message = new Alert(Alert.AlertType.WARNING);
            message.initOwner(tabsStage);
            message.setTitle("Avertizare");
            message.setContentText("Te rog sa selectezi o nevoie din lista!");
            message.showAndWait();
        }
    }

}
