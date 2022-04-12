package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.enums.ResidentFilterParameter;
import com.example.BarangayServicesclient.models.Resident;
import com.example.barangayservicesui.Main;
import com.example.barangayservicesui.utils.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.List;

public class ManageResidentController {
    private MainController mainController;
    private List<Resident> residents;
    private ResidentFilterParameter parameterType;
    private boolean isScanning = false;

    @FXML
    private ComboBox<String> cbFilter;

    @FXML
    private Button btnAddResident;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnScan;

    @FXML
    private ListView<Node> lvResidents;

    @FXML
    private TextField tfEntry;

    @FXML
    void selectParameter(ActionEvent event) {
        switch (cbFilter.getValue()) {
            case "RFID" -> parameterType = ResidentFilterParameter.ResidentRFID;
            case "Last Name" -> parameterType = ResidentFilterParameter.LastName;
            case "First Name" -> parameterType = ResidentFilterParameter.FirstName;
            case "Address" -> parameterType = ResidentFilterParameter.Address;
            case "Mobile Number" -> parameterType = ResidentFilterParameter.MobileNumber;
        }
    }

    @FXML
    void addResident(ActionEvent event)
            throws IOException {
        mainController.preCreateResident();
    }

    @FXML
    void searchResident(ActionEvent event)
            throws IOException {
        lvResidents.getItems().clear();

        if (tfEntry.getText().length() > 2){
            residents = Admin.getInstance()
                    .getResidentList(parameterType,
                            tfEntry.getText());
        }

        loadResidents();
    }

    @FXML
    void scanRFID(ActionEvent event) {
        if (!isScanning){ //change to scanning. waits for input from reader
            isScanning = true;
            tfEntry.setText("");
            tfEntry.requestFocus();

            btnScan.setText("Cancel");

        } else { //reset back to default
            onCompleteCancelScan();
        }
    }

    @FXML
    void enterKeyPressed(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)){
            onCompleteCancelScan();
            searchResident(null);
        }
    }

    private void onCompleteCancelScan() {
        isScanning = false;
        btnScan.setText("Scan RFID");
    }

    public void start(MainController mainController)
            throws IOException {
        this.mainController = mainController;
        cbFilter.getItems()
                .addAll(
                        ResidentFilterParameter.ResidentRFID.getParameter(),
                        ResidentFilterParameter.FirstName.getParameter(),
                        ResidentFilterParameter.LastName.getParameter(),
                        ResidentFilterParameter.Address.getParameter(),
                        ResidentFilterParameter.MobileNumber.getParameter());
    }

    private void loadResidents() throws IOException {
        for (Resident resident : residents){
            FXMLLoader loader = new FXMLLoader(Main.class
                    .getResource("views/card-view.fxml"));
            lvResidents.getItems()
                    .add(loader.load());

            //pass resident data to card
            CardController cardController =
                    loader.getController();
            cardController.initData(resident,
                    mainController);
        }
    }

}
