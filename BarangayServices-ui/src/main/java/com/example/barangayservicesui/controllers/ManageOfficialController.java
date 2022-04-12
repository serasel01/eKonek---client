package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.DatabaseFacade;
import com.example.BarangayServicesclient.enums.OfficialFilterParameter;
import com.example.BarangayServicesclient.models.Official;
import com.example.barangayservicesui.Main;
import com.example.barangayservicesui.utils.Admin;
import com.example.barangayservicesui.utils.LoaderUtil;
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

public class ManageOfficialController {
    private MainController mainController;
    private List<Official> officials;
    private OfficialFilterParameter parameterType;
    private boolean isScanning = false;

    @FXML
    private Button btnAddOfficial;

    @FXML
    private Button btnScan;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> cbFilter;

    @FXML
    private ListView<Node> lvOfficials;

    @FXML
    private TextField tfEntry;

    @FXML
    void addOfficial(ActionEvent event)
            throws IOException {

        LoaderUtil.getLoaderInstance()
                .loadOfficial();
    }

    @FXML
    void enterKeyPressed(KeyEvent event)
            throws IOException {

        if (event.getCode().equals(KeyCode.ENTER)){
            onCompleteCancelScan();
            searchOfficial(null);
        }
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
    void searchOfficial(ActionEvent event) throws IOException {
        lvOfficials.getItems().clear();

        if (tfEntry.getText().length() > 2){
            officials = DatabaseFacade
                    .getInstance()
                    .getOfficials(
                            Admin.getInstance()
                                    .getAdmin()
                                    .getBarangay(),
                            parameterType,
                            tfEntry.getText()
                    );
        }

        loadOfficials();
    }

    @FXML
    void selectParameter(ActionEvent event) {
        switch (cbFilter.getValue()) {
            case "RFID" -> parameterType = OfficialFilterParameter.OfficialRFID;
            case "Last Name" -> parameterType = OfficialFilterParameter.LastName;
            case "First Name" -> parameterType = OfficialFilterParameter.FirstName;
        }
    }

    public void start(MainController mainController) {
        this.mainController = mainController;
        cbFilter.getItems()
                .addAll(
                        OfficialFilterParameter.OfficialRFID.getParameter(),
                        OfficialFilterParameter.FirstName.getParameter(),
                        OfficialFilterParameter.LastName.getParameter()
                );
    }

    private void loadOfficials() throws IOException {
        for (Official official : officials){
            FXMLLoader loader = new FXMLLoader(Main.class
                    .getResource("views/card-view.fxml"));
            lvOfficials.getItems()
                    .add(loader.load());

            //pass resident data to card
            CardController cardController =
                    loader.getController();
            cardController.initData(official,
                    mainController);
        }
    }

    private void onCompleteCancelScan() {
        isScanning = false;
        btnScan.setText("Scan RFID");
    }

}
