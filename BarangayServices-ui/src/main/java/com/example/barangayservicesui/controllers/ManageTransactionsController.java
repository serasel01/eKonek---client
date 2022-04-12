package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.DatabaseFacade;
import com.example.BarangayServicesclient.enums.TransactionFilter;
import com.example.BarangayServicesclient.models.Transaction;
import com.example.barangayservicesui.utils.Admin;
import com.example.barangayservicesui.utils.LoaderUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ManageTransactionsController {
    private boolean isScanning = false;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnDirectory;

    @FXML
    private Button btnScan;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> cbCertificate;

    @FXML
    private ComboBox<String> cbFilter;

    @FXML
    private ComboBox<String> cbStatus;

    @FXML
    private DatePicker dpTransactionDate;

    @FXML
    private TextField tfEntry;

    @FXML
    private TableView<Transaction> tvTransactions;

    @FXML
    void createTransaction(ActionEvent event)
            throws IOException {
        LoaderUtil.getLoaderInstance()
                .loadTransaction(null);
    }

    @FXML
    void enterKeyPressed(KeyEvent event)
            throws SQLException {

        if (event.getCode().equals(KeyCode.ENTER)){
            onCompleteCancelScan();

            if (getParameterType() == TransactionFilter.ResidentName){
                displayTransactions(loadTransactions(
                        getParameterType(),
                        "%" + tfEntry.getText() + "%"));

            } else {
                displayTransactions(loadTransactions(
                        getParameterType(),
                        tfEntry.getText()));

            }
        }
    }

    @FXML
    void openDirectory(ActionEvent event)
            throws IOException {
        Desktop.getDesktop()
                .open(new File("src/main/resources/CreatedCertificates"));
    }

    @FXML
    void scanRFID(ActionEvent event) {
        if (!isScanning){ //change to scanning. waits for input from reader
            isScanning = true;
            tfEntry.setText("");
            tfEntry.requestFocus();

            btnSearch.setText("Cancel");

        } else { //reset back to default
            onCompleteCancelScan();
        }
    }

    @FXML
    void searchTransactions(ActionEvent event)
            throws SQLException {

        if (getParameterType() == TransactionFilter.ResidentName){
            displayTransactions(loadTransactions(
                    getParameterType(),
                    "%" + tfEntry.getText() + "%"));

        } else {
            displayTransactions(loadTransactions(
                    getParameterType(),
                    tfEntry.getText()));

        }
    }

    @FXML
    void selectDate(ActionEvent event)
            throws SQLException {

        displayTransactions(loadTransactions(
                TransactionFilter.TransactionDate,
                dpTransactionDate
                        .getValue()
                        .format(
                                DateTimeFormatter.ofPattern("LLLL dd, yyyy")
                        )
                )
        );
    }

    @FXML
    void selectCertificate(ActionEvent event)
            throws SQLException {

        displayTransactions(loadTransactions(
                TransactionFilter.TransactionType,
                cbCertificate.getValue()));
    }

    @FXML
    void selectStatus(ActionEvent event)
            throws SQLException {

        displayTransactions(loadTransactions(
                TransactionFilter.TransactionStatus,
                cbStatus.getValue()));
    }

    public void start(){
        initViews();
    }

    private void initViews() {
        cbCertificate.getItems()
                .addAll(
                        Admin.getInstance()
                                .getBarangay()
                                .getCertificateTypes()
                );

        cbFilter.getItems()
                .addAll(
                        TransactionFilter.TransactionID.getFilter(),
                        TransactionFilter.ResidentRFID.getFilter(),
                        TransactionFilter.ResidentName.getFilter());

        cbStatus.getItems()
                .addAll(
                       "PENDING",
                        "CANCELLED",
                        "COMPLETE");


        TableColumn idCol = new TableColumn("Transaction ID");
        idCol.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("transactionID")
        );

        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("transactionDate")
        );

        TableColumn typeCol = new TableColumn("Transaction Type");
        typeCol.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("transactionType")
        );

        TableColumn residentRFIDCol = new TableColumn("Resident RFID");
        residentRFIDCol.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("residentRFID")
        );

        TableColumn residentCol = new TableColumn("Resident");
        residentCol.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("residentName")
        );

        TableColumn officialCol = new TableColumn("Official");
        officialCol.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("officialName")
        );

        TableColumn statusCol = new TableColumn("Status");
        statusCol.setCellValueFactory(
                new PropertyValueFactory<Transaction, String>("transactionStatus")
        );

        tvTransactions.getColumns()
                .setAll(
                        idCol,
                        dateCol,
                        typeCol,
                        residentRFIDCol,
                        residentCol,
                        officialCol,
                        statusCol
                );

        tvTransactions.setRowFactory(tv -> {
            TableRow<Transaction> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Transaction transaction = row.getItem();

                    if (transaction.getTransactionStatus().equals("PENDING")){
                        try {
                            LoaderUtil.getLoaderInstance()
                                    .loadTransaction(transaction);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            return row ;
        });
    }

    private TransactionFilter getParameterType(){
        return switch (cbFilter.getValue()) {
            case "Resident Name" -> TransactionFilter.ResidentName;
            case "Resident RFID" -> TransactionFilter.ResidentRFID;
            case "Transaction ID" -> TransactionFilter.TransactionID;
            default -> null;
        };
    }

    private void displayTransactions(ObservableList<Transaction> logs) {
        tvTransactions.getItems().clear();
        tvTransactions.setItems(logs);
    }

    private ObservableList<Transaction> loadTransactions(TransactionFilter transactionFilter,
                                                         String parameterEntry)
            throws SQLException {

        return FXCollections.observableList(
                DatabaseFacade.getInstance()
                        .getTransactions(parameterEntry, transactionFilter)
        );
    }

    private void onCompleteCancelScan() {
        isScanning = false;
        btnScan.setText("Scan RFID");
    }

}
