package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.BarangayRESTClient;
import com.example.BarangayServicesclient.models.Log;
import com.example.barangayservicesui.utils.AdminPreferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class AdminController {
    private ObservableList<Log> logs;

    @FXML
    private TableView<Log> admin_tv_logs;


    public void start(){
        logs = FXCollections.observableList(
                BarangayRESTClient
                        .getInstance()
                        .getAllLogs(AdminPreferences
                                .getPrefsInstance().getUserBarangay()
                ));
        initViews();
    }

    private void initViews() {
        TableColumn dateTimeCol = new TableColumn("Date and Time");
        dateTimeCol.setCellValueFactory(
                new PropertyValueFactory<Log, String>("timestamp")
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

        admin_tv_logs.setItems(logs);
    }
}
