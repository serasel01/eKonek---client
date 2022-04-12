package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.DatabaseFacade;
import com.example.BarangayServicesclient.models.Case;
import com.example.BarangayServicesclient.models.Resident;
import com.example.BarangayServicesclient.models.Transaction;
import com.example.barangayservicesui.utils.Admin;
import com.example.barangayservicesui.utils.LoaderUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CaseController {
    private Resident resident;
    private Dialog<Boolean> dialog;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnSave;

    @FXML
    private DatePicker dpDateIssued;

    @FXML
    private DatePicker dpHearing;

    @FXML
    private TextArea tfAction;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfCaseNumber;

    @FXML
    private TextField tfCaseTitle;

    @FXML
    private TextField tfComplaint;

    @FXML
    private TextArea tfDescription;

    @FXML
    private TextField tfTime;

    @FXML
    private TableView<Case> tvCaseList;

    @FXML
    void closeWindow(ActionEvent event) {
        dialog.setResult(Boolean.TRUE);
        dialog.close();
    }

    @FXML
    void saveCase(ActionEvent event)
            throws JsonProcessingException {

        Case aCase = new Case(
                tfCaseTitle.getText(),
                tfCaseNumber.getText(),
                dpDateIssued.getValue()
                        .format(DateTimeFormatter
                                .ofPattern("LLLL dd, yyyy")),
                tfComplaint.getText(),
                tfAddress.getText(),
                tfDescription.getText(),
                dpHearing.getValue()
                        .format(DateTimeFormatter
                                .ofPattern("LLLL dd, yyyy")),
                tfTime.getText(),
                tfAction.getText()
        );

        Admin.getInstance()
                .addResidentCase(
                        resident,
                        aCase
                );

        displayCases(loadCases());
    }

    public void setData(Resident resident, Dialog<Boolean> dialog){
        this.resident = resident;
        this.dialog = dialog;
        initViews();
        setRestrictions();
    }

    private void setRestrictions() {
        if (Admin.getInstance()
                .getOfficial()
                .getUserType()
                .equals("Official")){
            btnSave.setDisable(true);
            btnSave.setVisible(false);
        }
    }

    private void initViews() {
        TableColumn caseIdCol = new TableColumn("Case Number");
        caseIdCol.setCellValueFactory(
                new PropertyValueFactory<Case, String>("caseNumber")
        );

        TableColumn caseNameCol = new TableColumn("Case Title");
        caseNameCol.setCellValueFactory(
                new PropertyValueFactory<Case, String>("caseTitle")
        );

        TableColumn dateCol = new TableColumn("Date Issued");
        dateCol.setCellValueFactory(
                new PropertyValueFactory<Case, String>("dateIssued")
        );

        tvCaseList.getColumns()
                .setAll(
                        caseIdCol,
                        caseNameCol,
                        dateCol
                );

        tvCaseList.setRowFactory(tv -> {
            TableRow<Case> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Case aCase = row.getItem();
                    tfCaseNumber.setText(aCase.getCaseNumber());
                    tfCaseTitle.setText(aCase.getCaseTitle());
                    dpDateIssued.setValue(LocalDate.parse(
                            aCase.getDateIssued(),
                            DateTimeFormatter.ofPattern("LLLL dd, yyyy")));
                    tfComplaint.setText(aCase.getComplaintName());
                    tfAddress.setText(aCase.getAddress());
                    tfDescription.setText(aCase.getDescription());
                    dpHearing.setValue(LocalDate.parse(
                            aCase.getDateHearing(),
                            DateTimeFormatter.ofPattern("LLLL dd, yyyy")));
                    tfTime.setText(aCase.getTimeHearing());
                    tfAction.setText(aCase.getActionsTaken());

                }
            });

            return row ;
        });

        displayCases(loadCases());
    }

    private void displayCases(ObservableList<Case> cases) {
        tvCaseList.getItems().clear();
        tvCaseList.setItems(cases);
    }

    private ObservableList<Case> loadCases() {
        return FXCollections.observableList(
                DatabaseFacade.getInstance()
                        .getCases(resident.getUserRFID())
        );
    }
}
