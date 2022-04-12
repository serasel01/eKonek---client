package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.DatabaseFacade;
import com.example.BarangayServicesclient.models.Official;
import com.example.BarangayServicesclient.models.Resident;
import com.example.barangayservicesui.enums.Pane;
import com.example.barangayservicesui.utils.LoaderUtil;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CardController {
    private Resident resident;
    private Official official;
    private MainController mainController;

    @FXML
    private HBox cardHolder;

    @FXML
    private ImageView ivPhoto;

    @FXML
    private Text txtName;

    @FXML
    private Text txtRFID;

    @FXML
    private Text txtUserType;

    @FXML
    void viewResident(MouseEvent event)
            throws IOException {

        if (resident == null){
            LoaderUtil
                    .getLoaderInstance()
                    .setOfficial(this.official)
                    .loadOfficial();

        } else {

            LoaderUtil.getLoaderInstance()
                    .setResident(resident);
            mainController.viewPane(Pane.Profile);
        }
    }

    //this is for loading the residents list
    public void initData(Resident resident,
                         MainController mainController)
            throws FileNotFoundException {

        this.resident = resident;
        this.mainController = mainController;

        txtName.setText(this.resident.getFullName());
        txtRFID.setText(this.resident.getUserRFID());

        try {
            ivPhoto.setImage(new Image(
                    new FileInputStream("src/main/resources/residentPhotos/" +
                            resident.getUserRFID() + ".png")
            ));

        } catch (Exception e){
            ivPhoto.setImage(new Image(
                    new FileInputStream("src/main/resources/images/UserPlate.png")));
        }


    }

    //this is for loading the officials list
    public void initData(Official official,
                         MainController mainController)
            throws FileNotFoundException {
        this.mainController = mainController;
        this.official = official;

        txtName.setText(official.getFullName());
        txtRFID.setText(official.getUserRFID());
        txtUserType.setText(official.getUserType());

        try {
            ivPhoto.setImage(new Image(
                    new FileInputStream("src/main/resources/residentPhotos/" +
                            official.getUserRFID() + ".png")
            ));

        } catch (Exception e){
            ivPhoto.setImage(new Image(
                    new FileInputStream("src/main/resources/images/UserPlate.png")));
        }

    }

}
