package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.RESTFacade;
import com.example.BarangayServicesclient.enums.ParameterType;
import com.example.BarangayServicesclient.models.Log;
import com.example.barangayservicesui.utils.Admin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.time.ZoneId;

public class AdminController {
    private boolean isScanning = false;

    @FXML
    private Button admin_btn_search;

    @FXML
    private Button admin_btn_rfid;

    @FXML
    private ComboBox<String> admin_cb_event;

    @FXML
    private ComboBox<String> admin_cb_parameter;

    @FXML
    private DatePicker admin_dp_date;

    @FXML
    private TextField admin_tf_entry;

    @FXML
    private TableView<Log> admin_tv_logs;

    @FXML
    void searchLogs(ActionEvent event) {
        displayLogs(loadLogs(getParameterType(), admin_tf_entry.getText()));
    }

    @FXML
    void selectEvent(ActionEvent event) {
        displayLogs(loadLogs(ParameterType.Event, admin_cb_event.getValue()));
    }

    @FXML
    void selectLogDate(ActionEvent event) {
        displayLogs(loadLogs(
                ParameterType.Timestamp,
                String.valueOf(admin_dp_date.getValue()
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli()
                )
        ));
    }

    @FXML
    void scanRFID(ActionEvent event) {
        if (!isScanning){ //change to scanning. waits for input from reader
            isScanning = true;
            admin_tf_entry.setText("");
            admin_tf_entry.requestFocus();

            admin_btn_rfid.setText("Cancel");

        } else { //reset back to default
            onCompleteCancelScan();
        }
    }

    @FXML
    void enterKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)){
            onCompleteCancelScan();
            displayLogs(loadLogs(getParameterType(), admin_tf_entry.getText()));
        }
    }

    private void onCompleteCancelScan() {
        isScanning = false;
        admin_btn_rfid.setText("Scan RFID");
    }

    private ObservableList<Log> loadLogs(ParameterType parameterType,
                                         String parameterEntry) {
        return FXCollections.observableList(
                RESTFacade.getInstance().getAllLogs(Admin.getInstance().getAdmin()
                                .getBarangay(),
                        parameterType,
                        parameterEntry
                ));
    }


    public void start(){
        initViews();
    }

    private void initViews() {
        admin_cb_event.getItems()
                .addAll("Admin Account Creation",
                        "Admin Account Deletion",
                        "Resident Account Creation",
                        "Resident Account Deletion",
                        "Update Resident Info",
                        "Certificate Issuance");

        admin_cb_parameter.getItems()
                .addAll("Resident Name",
                        "Admin Name",
                        "Resident RFID",
                        "Admin RFID");

        TableColumn dateTimeCol = new TableColumn("Date and Time");
        dateTimeCol.setCellValueFactory(
                new PropertyValueFactory<Log, String>("dateTime")
        );

        TableColumn eventCol = new TableColumn("Event");
        eventCol.setCellValueFactory(
                new PropertyValueFactory<Log, String>("event")
        );

        TableColumn adminCol = new TableColumn("Admin");
        adminCol.setCellValueFactory(
                new PropertyValueFactory<Log, String>("adminName")
        );

        TableColumn residentCol = new TableColumn("Resident");
        residentCol.setCellValueFactory(
                new PropertyValueFactory<Log, String>("residentName")
        );

        admin_tv_logs.getColumns()
                .setAll(
                        dateTimeCol,
                        eventCol,
                        adminCol,
                        residentCol
                );
    }

    private void displayLogs(ObservableList<Log> logs) {
        admin_tv_logs.getItems().clear();
        admin_tv_logs.setItems(logs);
    }

    private ParameterType getParameterType(){
        switch (admin_cb_parameter.getValue()){
            case "Resident Name":
                return ParameterType.ResidentName;
            case "Admin Name":
                return ParameterType.AdminName;
            case "Resident RFID":
                return ParameterType.ResidentRFID;
            case "Admin RFID":
                return ParameterType.AdminRFID;
        }
        return null;
    }




}
