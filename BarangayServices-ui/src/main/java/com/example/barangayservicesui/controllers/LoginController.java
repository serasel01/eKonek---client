package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.DatabaseFacade;
import com.example.BarangayServicesclient.models.Official;
import com.example.BarangayServicesclient.models.Resident;
import com.example.barangayservicesui.enums.Barangay;
import com.example.barangayservicesui.enums.LogEvent;
import com.example.barangayservicesui.utils.Admin;
import com.example.barangayservicesui.utils.LoaderUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class LoginController {
    private HashMap<String, Image> barangays;
    private boolean isScanning = false;
    private String userRFID;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnScan;

    @FXML
    private ComboBox<String> cbBarangays;

    @FXML
    private ImageView ivSeal;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfRfid;

    @FXML
    private Text txtMessage;

    @FXML
    void loginAdmin(ActionEvent event) throws IOException {
        if (!isInputEmpty()){
            authenticateUser();
        }
    }

    @FXML
    void selectBarangay(ActionEvent event) {
        ivSeal.setImage(barangays.get(cbBarangays.getValue()));
    }

    @FXML
    void enterKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)){
            pfPassword.requestFocus();
            userRFID = tfRfid.getText();
            onCompleteCancelScan();
        }
    }

    @FXML
    void loginByEnter(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)){
            if (!isInputEmpty()){
                authenticateUser();
            }
        }
    }

    @FXML
    void scanRFID(ActionEvent event) {
        if (!isScanning){ //change to scanning. waits for input from reader
            isScanning = true;
            tfRfid.setEditable(true);
            tfRfid.setText("");
            tfRfid.requestFocus();

            txtMessage.setText("Please scan your RFID.");
            btnScan.setText("Cancel");

        } else { //reset back to default
            onCompleteCancelScan();
        }
    }

    public void start() throws FileNotFoundException {
        initBarangays();
    }

    private void initBarangays() throws FileNotFoundException {
        barangays = new HashMap<>();

        for (Barangay barangay: Barangay.values()){
            barangays.put(barangay.getBarangay(),
                    new Image(new FileInputStream(barangay.getFileReference()))
            );
            cbBarangays.getItems().add(barangay.getBarangay());
        }

        ivSeal.setImage(barangays.get("Guiwan"));
        tfRfid.setEditable(false);
    }

    private boolean isInputEmpty() {
        if (tfRfid.getText().isEmpty()){
            txtMessage.setText("Please scan your RFID.");
            return true;

        } else if (pfPassword.getText().isEmpty()){
            txtMessage.setText("Please enter your password.");
            return true;

        } else if (cbBarangays.getValue() == null){
            txtMessage.setText("Please select your barangay.");
            return true;

        } else {
            return false;
        }
    }

    private void authenticateUser() throws IOException {
        txtMessage.setText("Logging in. Please wait.");

        String hashedPassword = DigestUtils.sha256Hex(pfPassword.getText());
        String result = DatabaseFacade
                .getInstance()
                .authenticateLogin(
                        userRFID,
                        hashedPassword,
                        cbBarangays.getValue()
                );

        if (result.equals("Login Success!")){
            setAdminValues();

            Admin.getInstance()
                    .addLog(LogEvent.Login.getEvent());

            LoaderUtil.getLoaderInstance()
                    .showMain();
        }

        txtMessage.setText(result);
    }

    private void setAdminValues() {
        Official official = DatabaseFacade
                .getInstance()
                .getOfficial(
                        userRFID,
                        cbBarangays.getValue());

        Resident admin = DatabaseFacade
                .getInstance()
                .getResident(userRFID);

        Admin.getInstance().setAdmin(admin);
        Admin.getInstance().setOfficial(official);
    }

    private void onCompleteCancelScan() {
        isScanning = false;
        tfRfid.setEditable(false);
        btnScan.setText("Scan");
        txtMessage.setText("");
        tfRfid.setText(userRFID);
    }

}
