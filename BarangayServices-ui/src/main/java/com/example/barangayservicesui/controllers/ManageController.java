package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.enums.ParameterType;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ManageController{
    private MainController mainController;
    private List<Resident> residents;
    private ParameterType parameterType;
    private boolean isScanning = false;

    @FXML
    private ComboBox<String> info_cb_parameter;

    @FXML
    private Button manage_btn_add;

    @FXML
    private Button manage_btn_search;

    @FXML
    private Button manage_btn_rfid;

    @FXML
    private ListView<Node> manage_lv_residents;

    @FXML
    private TextField manage_tf_entry;

    @FXML
    void selectParameter(ActionEvent event) {
        switch (info_cb_parameter.getValue()){
            case "RFID":
                parameterType = ParameterType.ResidentRFID;
                break;
            case "Last Name":
                parameterType = ParameterType.ResidentName;
                break;
        }
    }

    @FXML
    void addResident(ActionEvent event)
            throws FileNotFoundException {
        mainController.viewResidentPane(null);
    }

    @FXML
    void searchResident(ActionEvent event)
            throws IOException {
        manage_lv_residents.getItems().clear();

        if (manage_tf_entry.getText().length() > 2){
            residents = Admin.getInstance()
                    .getResidentList(parameterType,
                            manage_tf_entry.getText());
        }

        loadResidents();
    }

    @FXML
    void scanRFID(ActionEvent event) {
        if (!isScanning){ //change to scanning. waits for input from reader
            isScanning = true;
            manage_tf_entry.setText("");
            manage_tf_entry.requestFocus();

            manage_btn_rfid.setText("Cancel");

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
        manage_btn_rfid.setText("Scan RFID");
    }

    public void start(MainController mainController)
            throws IOException {
        this.mainController = mainController;
        info_cb_parameter.getItems()
                .addAll("RFID", "Last Name");
    }

    private void loadResidents() throws IOException {
        for (Resident resident : residents){
            FXMLLoader loader = new FXMLLoader(Main.class
                    .getResource("views/card-view.fxml"));
            manage_lv_residents.getItems()
                    .add(loader.load());

            //pass resident data to card
            CardController cardController =
                    loader.getController();
            cardController.initData(resident,
                    mainController);
        }
    }

}
