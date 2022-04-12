package com.example.barangayservicesui.controllers;

import com.example.barangayservicesui.enums.Pane;
import com.example.barangayservicesui.utils.LoaderUtil;
import com.example.barangayservicesui.utils.Admin;
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

public class MainController {
    private ObservableList<Node> childs;

    @FXML
    private Button btnLogs;

    @FXML
    private Button btnOfficials;

    @FXML
    private Button btnProfile;

    @FXML
    private Button btnResidents;

    @FXML
    private Button btnTransactions;

    @FXML
    private ImageView ivSeal;

    @FXML
    private HBox nodeHolder;

    @FXML
    private Text tfBarangay;

    @FXML
    private Text tfName;

    @FXML
    private Text tfRFID;

    @FXML
    void switchToLogs(ActionEvent event)
            throws IOException {
        viewPane(Pane.SystemLogs);
    }

    @FXML
    void switchToOfficials(ActionEvent event)
            throws IOException {
        viewPane(Pane.Officials);
    }

    @FXML
    void switchToTransactions(ActionEvent event)
            throws IOException {
        viewPane(Pane.Transactions);
    }

    @FXML
    void switchToProfile(ActionEvent event)
            throws IOException {
        LoaderUtil
                .getLoaderInstance()
                .setResident(Admin
                        .getInstance()
                        .getAdmin()
                );
        viewPane(Pane.Profile);
    }

    @FXML
    void switchToResidents(ActionEvent event)
            throws IOException {
        viewPane(Pane.Residents);
    }

    public void logout(){

    }

    //this function does the setup
    public void start(StackPane stackPane)
            throws IOException {
        displayUser();

        nodeHolder.getChildren().add(stackPane);
        childs = stackPane.getChildren();
    }

    public void displayUser() throws FileNotFoundException {
        tfBarangay.setText(Admin.getInstance()
                .getAdmin()
                .getBarangay());

        tfName.setText(Admin.getInstance()
                .getAdmin()
                .getFullName());

        tfRFID.setText(Admin.getInstance()
                .getAdmin()
                .getUserRFID());

        ivSeal.setImage(new Image(
                new FileInputStream(Admin.getInstance()
                        .getBarangay()
                        .getFileReference())));

        setRestrictions();
    }

    private void setRestrictions() {
        if (Admin.getInstance()
                .getOfficial()
                .getUserType()
                .equals("Secretary")){

            btnLogs.setDisable(true);
            btnLogs.setVisible(false);

        } else if (Admin.getInstance()
                .getOfficial()
                .getUserType()
                .equals("Official")){

            btnLogs.setDisable(true);
            btnLogs.setVisible(false);

            btnOfficials.setDisable(true);
            btnOfficials.setVisible(false);

            btnResidents.setDisable(true);
            btnResidents.setVisible(false);

        }
    }

    public void viewPane(Pane pane) throws IOException {
        LoaderUtil.getLoaderInstance()
                .deleteNode();
        LoaderUtil.getLoaderInstance()
                .loadNode(pane);
    }

    //this function is used for creating resident accounts
    public void preCreateResident() throws IOException {
        viewPane(Pane.NewProfile);
    }

}
