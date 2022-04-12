package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.DatabaseFacade;
import com.example.BarangayServicesclient.models.Resident;
import com.example.BarangayServicesclient.models.Transaction;
import com.example.barangayservicesui.certificates.Certificate;
import com.example.barangayservicesui.certificates.CertificateFactory;
import com.example.barangayservicesui.enums.LogEvent;
import com.example.barangayservicesui.enums.Pane;
import com.example.barangayservicesui.utils.Admin;
import com.example.barangayservicesui.utils.AlertManager;
import com.example.barangayservicesui.utils.LoaderUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionController {
    private Dialog<Boolean> dialog;
    private boolean isScanning = false;
    private Resident resident;
    private Transaction transaction;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCloseWindow;

    @FXML
    private Button btnCreateTransaction;

    @FXML
    private Button btnScan;

    @FXML
    private HBox card_ap_holder;

    @FXML
    private ComboBox<String> cbCertificate;

    @FXML
    private ImageView ivPhoto;

    @FXML
    private TextField tfRFID;

    @FXML
    private Text txtName;

    @FXML
    private Text txtRFID;

    @FXML
    void cancelTransaction(ActionEvent event)
            throws JsonProcessingException {

        transaction.setTransactionStatus("CANCELLED");
        DatabaseFacade.getInstance()
                .updateTransaction(transaction);

        Admin.getInstance()
                .addLog(LogEvent.TransactionCancellation.getEvent());


        closeWindow(null);
    }

    @FXML
    void closeWindow(ActionEvent event) {
        dialog.setResult(Boolean.TRUE);
        dialog.close();
    }

    @FXML
    void createTransaction(ActionEvent event)
            throws SQLException, IOException {

        if (this.transaction == null){
            Transaction newTransaction = new Transaction(
                    generateTransactionID(),
                    LocalDate.now().format(
                            DateTimeFormatter.ofPattern("LLLL dd, yyyy")),
                    cbCertificate.getValue(),
                    resident.getFullName(),
                    Admin.getInstance().getAdmin().getFullName(),
                    "PENDING",
                    resident.getUserRFID()
            );

            DatabaseFacade.getInstance()
                    .addTransaction(newTransaction);

            Admin.getInstance()
                    .addLog(LogEvent.TransactionCreation.getEvent());

        } else {
            if (this.transaction.getTransactionStatus()
                    .equals("PENDING")){

                this.transaction.setTransactionStatus("COMPLETE");
                DatabaseFacade.getInstance()
                        .updateTransaction(this.transaction);

                generateCertificate();

                Admin.getInstance()
                        .addLog(LogEvent.TransactionCompletion.getEvent());


            }
        }

        closeWindow(null);
    }

    private void generateCertificate()
            throws IOException {

        Certificate certificate = new CertificateFactory()
                .getCertificate(
                        cbCertificate.getValue(),
                        resident
                );

        certificate.createCertificate(
                certificate.mapDocContent(),
                certificate.getDocument()
        );

        certificate.saveCertificate(certificate.getDocument());

        new AlertManager(Alert.AlertType.INFORMATION)
                .setMessage("Certificate Created Successfully")
                .show();
    }

    private String generateTransactionID()
            throws SQLException {

        String initials = switch (cbCertificate.getValue()) {
            case "Certification" -> "CT";
            case "Clearance" -> "BC";
            case "Senior Citizen" -> "SC";
            case "Good Moral" -> "GM";
            case "Residency" -> "BR";
            case "Indigency" -> "BI";
            default -> "";
        };

        int count = DatabaseFacade
                .getInstance()
                .getTransactionsCount(
                        cbCertificate.getValue()
                );

        String dateTime = LocalDateTime
                .now()
                .format(
                        DateTimeFormatter
                                .ofPattern("MMddyyyyhhmm")
                );

        return initials + count + dateTime;
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
    void selectCertificate(ActionEvent event) {

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
        initViews();
    }

    public void setData(Transaction transaction)
            throws FileNotFoundException {

        this.transaction = transaction;
        tfRFID.setText(this.transaction.getResidentRFID());
        btnCreateTransaction.setText("Complete Transaction");

        showCancelButton();

        cbCertificate.setValue(transaction.getTransactionType());
        displayResident();
    }

    private void initViews() {
        cbCertificate.getItems()
                .addAll(
                        Admin.getInstance()
                                .getBarangay()
                                .getCertificateTypes()
                );

        hideCancelButton();
        setRestrictions();
    }

    private void hideCancelButton() {
        btnCancel.setDisable(true);
        btnCancel.setVisible(false);
    }

    private void showCancelButton() {
        btnCancel.setDisable(false);
        btnCancel.setVisible(true);
    }

    private void setRestrictions() {
        if (Admin.getInstance()
                .getOfficial()
                .getUserType()
                .equals("Official")){

            btnCreateTransaction.setVisible(false);
            btnCreateTransaction.setDisable(true);
        }
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
    }

    private void onCompleteCancelScan() {
        isScanning = false;
        btnScan.setText("Scan RFID");
    }
}
