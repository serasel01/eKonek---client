package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.BarangayRESTClient;
import com.example.BarangayServicesclient.models.Resident;
import com.example.barangayservicesui.utils.AdminPreferences;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class MainController {

    private ObservableList<Node> childs;
    private HashMap<Integer, Object> controllers;

    //nodeIndex: 1 - Manage Residents
    //           2 - View Profile
    //           3 - View Admin Activity
    private int nodeIndex = 1;

    @FXML
    private Button main_btn_admin;

    @FXML
    private Button main_btn_logout;

    @FXML
    private Button main_btn_profile;

    @FXML
    private Button main_btn_residents;

    @FXML
    private Text main_tf_barangay;

    @FXML
    private Text main_tf_name;

    @FXML
    private Text main_tf_rfid;

    @FXML
    private HBox main_hbox;

    @FXML
    private ImageView main_iv_seal;

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void switchToAdmin(ActionEvent event) {
        switch (nodeIndex) {
            case 1 -> {
                childs.get(childs.size() - 1).toBack();
                childs.get(childs.size() - 1).toBack();
                nodeIndex = 3;
            }
            case 2 -> {
                childs.get(childs.size() - 1).toBack();
                nodeIndex = 3;
            }
        }
    }

    @FXML
    void switchToManage(ActionEvent event) throws IOException {
        viewManagePane();
    }

    @FXML
    void switchToProfile(ActionEvent event) throws IOException {
        viewResidentInfo(getAdminData());
    }

    public void viewResidentInfo(Resident resident) throws FileNotFoundException {
        ProfileController profileController =
                (ProfileController) controllers.get(2);

        //if not null, Admin's Data is displayed
        if (resident != null){
            profileController.initData(resident);

        //if null, Blank form for New Resident
        } else {
            profileController.clearData();
        }

        //actual switching of nodes
        switch (nodeIndex) {
            case 3 -> {
                childs.get(childs.size() - 1).toBack();
                childs.get(childs.size() - 1).toBack();
                nodeIndex = 2;
            }
            case 1 -> {
                childs.get(childs.size() - 1).toBack();
                nodeIndex = 2;
            }
        }
    }

    private Resident getAdminData() {
        return BarangayRESTClient
                .getInstance()
                .getResident(
                        AdminPreferences.getPrefsInstance().getUserBarangay(),
                        AdminPreferences.getPrefsInstance().getUserRfid()
                );
    }

    public void viewManagePane(){
        switch (nodeIndex) {
            case 2 -> {
                childs.get(childs.size() - 1).toBack();
                childs.get(childs.size() - 1).toBack();
                nodeIndex = 1;
            }
            case 3 -> {
                childs.get(childs.size() - 1).toBack();
                nodeIndex = 1;
            }
        }
    }

    public void start(StackPane stackPane, HashMap<Integer, Object> controllers) throws IOException {
        initAdmin();

        main_hbox.getChildren().add(stackPane);
        childs = stackPane.getChildren();

        this.controllers = controllers;
    }

    private void initAdmin() throws FileNotFoundException {
//        AdminPreferences adminPreferences = new AdminPreferences();
        main_tf_barangay.setText(AdminPreferences.getPrefsInstance().getUserBarangay());
        main_tf_name.setText(AdminPreferences.getPrefsInstance().getName());
        main_tf_rfid.setText(String.valueOf(AdminPreferences.getPrefsInstance().getUserRfid()));
        main_iv_seal.setImage(new Image(new FileInputStream(AdminPreferences.getPrefsInstance().getUserSeal())));
    }
}