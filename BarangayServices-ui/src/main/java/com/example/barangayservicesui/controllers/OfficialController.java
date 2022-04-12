package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.DatabaseFacade;
import com.example.BarangayServicesclient.models.Official;
import com.example.BarangayServicesclient.models.Resident;
import com.example.BarangayServicesclient.models.Transaction;
import com.example.barangayservicesui.enums.LogEvent;
import com.example.barangayservicesui.enums.Pane;
import com.example.barangayservicesui.utils.Admin;
import com.example.barangayservicesui.utils.AlertManager;
import com.example.barangayservicesui.utils.LoaderUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class OfficialController {
    private Dialog<Boolean> dialog;
    private boolean isScanning = false;
    private Resident resident;
    private Official official;

    @FXML
    private Button btnAddOfficial;

    @FXML
    private Button btnCloseWindow;

    @FXML
    private Button btnScan;

    @FXML
    private HBox card_ap_holder;

    @FXML
    private ImageView ivPhoto;

    @FXML
    private TextField tfRFID;

    @FXML
    private Text txtName;

    @FXML
    private Text txtRFID;

    @FXML
    void addOfficial(ActionEvent event)
            throws JsonProcessingException {

        if (this.official == null){

            if (new AlertManager(Alert.AlertType.CONFIRMATION)
                    .setMessage("Are you sure to promote this resident to barangay official?")
                    .showAndWait()){

                Official official = new Official(
                        txtRFID.getText(),
                        "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918",
                        "Official",
                        resident.getBarangay(),
                        resident.getFirstName(),
                        resident.getLastName()
                );

                DatabaseFacade.getInstance()
                        .addOfficial(official);

                Admin.getInstance().addLog(LogEvent.OfficialAccountCreation.getEvent());
            }

        } else {

            if (new AlertManager(Alert.AlertType.CONFIRMATION)
                    .setMessage("Are you sure to demote this barangay official?")
                    .showAndWait()){

                DatabaseFacade.getInstance()
                        .deleteOfficial(this.official.getUserRFID());

                Admin.getInstance().addLog(LogEvent.OfficialAccountDeletion.getEvent());

            }

        }

        closeWindow(null);
    }

    @FXML
    void closeWindow(ActionEvent event) {
        dialog.setResult(Boolean.TRUE);
        dialog.close();
    }

    @FXML
    void enterKeyPressed(KeyEvent event)
            throws FileNotFoundException {

        if (event.getCode().equals(KeyCode.ENTER)){
            onCompleteCancelScan();
            displayResident();
        }
    }

    @FXML
    void scanRFID(ActionEvent event) {
        if (!isScanning){ //change to scanning. waits for input from reader
            isScanning = true;
            tfRFID.setText("");
            tfRFID.requestFocus();

            btnScan.setText("Cancel");

        } else { //reset back to default
            onCompleteCancelScan();
        }
    }

    @FXML
    void viewResident(MouseEvent event)
            throws IOException {

        LoaderUtil.getLoaderInstance()
                .setResident(resident);
        LoaderUtil.getLoaderInstance()
                .loadNode(Pane.Profile);
    }

    public void start(Dialog<Boolean> dialog) {
        this.dialog = dialog;
    }

    public void setData(Official official)
            throws FileNotFoundException {

        this.official = official;
        tfRFID.setText(this.official.getUserRFID());
        btnAddOfficial.setText("Remove Official");

        displayResident();
    }

    private void displayResident()
            throws FileNotFoundException {

        resident = DatabaseFacade
                .getInstance()
                .getResident(tfRFID.getText());

        txtRFID.setText(resident.getUserRFID());
        txtName.setText(resident.getFullName());

        try {
            ivPhoto.setImage(new Image(
                    new FileInputStream("src/main/resources/residentPhotos/"
                            + resident.getUserRFID() + ".png")));

        } catch (Exception e){
            ivPhoto.setImage(new Image(
                    new FileInputStream("src/main/resources/images/UserPlate.png")));
        }

        if (official != null && official.getUserType().equals("Captain")
                || official.getUserType().equals("Secretary")){
            btnAddOfficial.setVisible(false);
            btnAddOfficial.setDisable(true);
        }
    }

    private void onCompleteCancelScan() {
        isScanning = false;
        btnScan.setText("Scan RFID");
    }

}
