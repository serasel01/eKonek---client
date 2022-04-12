package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.DatabaseFacade;
import com.example.BarangayServicesclient.enums.SystemEventFilter;
import com.example.BarangayServicesclient.models.SystemEvent;
import com.example.BarangayServicesclient.models.Transaction;
import com.example.barangayservicesui.enums.LogEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class LogController {
    private boolean isScanning = false;

    @FXML
    private Button btnScan;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> cbFilter;

    @FXML
    private ComboBox<String> cbLogEvent;

    @FXML
    private DatePicker dpLogDate;

    @FXML
    private TextField tfEntry;

    @FXML
    private TableView<SystemEvent> tvLogs;

    @FXML
    void enterKeyPressed(KeyEvent event)
            throws SQLException {

        if (event.getCode().equals(KeyCode.ENTER)){
            onCompleteCancelScan();
            searchLogs(null);
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
    void searchLogs(ActionEvent event)
            throws SQLException {

        displayLogs(loadLogs(
                getParameterType(),
                tfEntry.getText()));
    }

    @FXML
    void selectEvent(ActionEvent event)
            throws SQLException {
        displayLogs(loadLogs(
                SystemEventFilter.Event,
                cbLogEvent.getValue()));
    }

    @FXML
    void selectDate(ActionEvent event) throws SQLException {
        displayLogs(loadLogs(
                        SystemEventFilter.Timestamp,
                        dpLogDate
                                .getValue()
                                .format(
                                        DateTimeFormatter.ofPattern("LLLL dd, yyyy%")
                                )
                )
        );
    }

    @FXML
    void selectParameter(ActionEvent event) {

    }

    public void start(){
        initViews();
    }

    private void initViews() {
        cbLogEvent.getItems()
                .addAll(
                        LogEvent.OfficialAccountCreation.getEvent(),
                        LogEvent.OfficialAccountDeletion.getEvent(),
                        LogEvent.ResidentAccountCreation.getEvent(),
                        LogEvent.ResidentAccountDeletion.getEvent(),
                        LogEvent.UpdateResidentInfo.getEvent(),
                        LogEvent.TransactionCreation.getEvent(),
                        LogEvent.TransactionCompletion.getEvent(),
                        LogEvent.TransactionCancellation.getEvent(),
                        LogEvent.Login.getEvent(),
                        LogEvent.Logout.getEvent()
                );

        cbFilter.getItems()
                .addAll(
                        SystemEventFilter.OfficialID.getFilter(),
                        SystemEventFilter.OfficialName.getFilter()
                );

        TableColumn timeCol = new TableColumn("Timestamp");
        timeCol.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("timestamp")
        );

        TableColumn eventCol = new TableColumn("Event");
        eventCol.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("event")
        );

        TableColumn idCol = new TableColumn("Official RFID");
        idCol.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("officialID")
        );

        TableColumn nameCol = new TableColumn("Official Name");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("officialName")
        );

        tvLogs.getColumns()
                .setAll(
                        timeCol,
                        eventCol,
                        idCol,
                        nameCol
                );
    }

    private void onCompleteCancelScan() {
        isScanning = false;
        btnScan.setText("Scan RFID");
    }

    private void displayLogs(ObservableList<SystemEvent> logs) {
        tvLogs.getItems().clear();
        tvLogs.setItems(logs);
    }

    private ObservableList<SystemEvent> loadLogs(SystemEventFilter filter,
                                                 String parameterEntry)
            throws SQLException {

        return FXCollections.observableList(
                DatabaseFacade.getInstance()
                        .getSystemEvents(parameterEntry, filter)
        );
    }

    private SystemEventFilter getParameterType(){
        return switch (cbFilter.getValue()) {
            case "Official Name" -> SystemEventFilter.OfficialName;
            case "Official ID" -> SystemEventFilter.OfficialID;
            default -> null;
        };
    }

}

