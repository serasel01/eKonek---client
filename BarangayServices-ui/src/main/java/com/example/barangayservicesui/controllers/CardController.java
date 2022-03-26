package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.models.Resident;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;

public class CardController {
    private Resident resident;
    private MainController mainController;

    @FXML
    private HBox card_ap_holder;

    @FXML
    private ImageView card_iv_photo;

    @FXML
    private Text card_txt_name;

    @FXML
    private Text card_txt_rfid;

    @FXML
    void viewResident(MouseEvent event)
            throws FileNotFoundException {
        mainController.viewResident(resident);
    }

    public void initData(Resident resident, MainController mainController){
        this.resident = resident;
        this.mainController = mainController;

        card_txt_name.setText(
                this.resident.getFirstName()
                        + " " + this.resident.getMiddleName()
                        + " " + this.resident.getLastName()
        );
        card_txt_rfid.setText(this.resident.getUserRFID());
    }

}
