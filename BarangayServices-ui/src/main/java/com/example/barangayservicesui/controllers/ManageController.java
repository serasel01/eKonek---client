package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.BarangayRESTClient;
import com.example.BarangayServicesclient.models.Resident;
import com.example.barangayservicesui.Main;
import com.example.barangayservicesui.utils.AdminPreferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ManageController{
    private MainController mainController;
    private List<Resident> residents;

    @FXML
    private Button manage_btn_add;

    @FXML
    private Button manage_btn_search;

    @FXML
    private ListView<Node> manage_lv_residents;

    @FXML
    private TextField manage_tf_entry;

    @FXML
    void addResident(ActionEvent event) throws FileNotFoundException {
        mainController.viewResidentInfo(null);
    }

    @FXML
    void searchResident(ActionEvent event) throws IOException {
        manage_lv_residents.getItems().clear();

        if (manage_tf_entry.getText().length() > 2){
            residents = BarangayRESTClient.getInstance()
                    .getSearchedResidents(AdminPreferences.getPrefsInstance()
                            .getUserBarangay(), manage_tf_entry.getText());

        } else if (manage_tf_entry.getText().length() == 0){
            residents = BarangayRESTClient.getInstance()
                    .getAllResidents(AdminPreferences.getPrefsInstance()
                            .getUserBarangay());
        }

        loadResidents();
    }

    public void start(MainController mainController) throws IOException {
        this.mainController = mainController;

        residents = BarangayRESTClient.getInstance()
                .getAllResidents(AdminPreferences.getPrefsInstance()
                        .getUserBarangay());

        loadResidents();
    }

    private void loadResidents() throws IOException {
        for (Resident resident : residents){
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/card-view.fxml"));
            manage_lv_residents.getItems().add(loader.load());

            //pass resident data to card
            CardController cardController = loader.getController();
            cardController.initData(resident, mainController);
        }
    }

}
