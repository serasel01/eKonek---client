package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.DatabaseFacade;
import com.example.BarangayServicesclient.models.Official;
import com.example.barangayservicesui.enums.LogEvent;
import com.example.barangayservicesui.utils.Admin;
import com.example.barangayservicesui.utils.AlertManager;
import com.example.barangayservicesui.utils.LoaderUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

public class AccountSettingsController {
    private String userRFID;
    private Dialog<Boolean> dialog;

    @FXML
    private Button btnChangePass;

    @FXML
    private Button btnCloseWindow;

    @FXML
    private Button btnLogout;

    @FXML
    private HBox card_ap_holder;

    @FXML
    private PasswordField pfCurrent;

    @FXML
    private PasswordField pfNew;

    @FXML
    private PasswordField pfRetype;

    @FXML
    private Text txtRFID;

    @FXML
    void changePassword(ActionEvent event)
            throws JsonProcessingException {

        Official official = Admin
                .getInstance()
                .getOfficial();

        String hashedPassword = DigestUtils
                .sha256Hex(pfCurrent.getText());

        if (pfNew.getText().equals(pfRetype.getText())
                && hashedPassword.equals(official.getPassword())){

            official.setPassword(
                    DigestUtils.sha256Hex(
                            pfNew.getText()
                    ));

            DatabaseFacade
                    .getInstance()
                    .updateOfficial(official);

            new AlertManager(Alert.AlertType.INFORMATION)
                    .setMessage("Password Changed Successfully!").show();

        } else if (!pfNew.getText().equals(pfRetype.getText())) {
            new AlertManager(Alert.AlertType.ERROR)
                    .setMessage("New passwords do not match.").show();


        } else if (!hashedPassword.equals(official.getPassword())){
            new AlertManager(Alert.AlertType.ERROR)
                    .setMessage("Wrong current password!").show();
        }

    }

    @FXML
    void closeWindow(ActionEvent event) {
        dialog.setResult(Boolean.TRUE);
        dialog.close();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Admin.getInstance().addLog(LogEvent.Logout.getEvent());

        Admin.getInstance()
                .logout();
        LoaderUtil.getLoaderInstance()
                .showLogin();

        dialog.setResult(Boolean.TRUE);
        dialog.close();
    }

    @FXML
    void viewResident(MouseEvent event) {

    }

    public void setData(String userRFID, Dialog<Boolean> dialog){
        this.userRFID = userRFID;
        this.dialog = dialog;
    }

}
