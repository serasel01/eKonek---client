package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.RESTFacade;
import com.example.barangayservicesui.enums.Barangay;
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
    private Button login_btn_login;

    @FXML
    private Button login_btn_scan;

    @FXML
    private ComboBox<String> login_cb_barangay;

    @FXML
    private ImageView login_iv_seal;

    @FXML
    private PasswordField login_pf_password;

    @FXML
    private TextField login_tf_rfid;

    @FXML
    private Text login_tf_message;

    @FXML
    void loginAdmin(ActionEvent event) throws IOException {
        if (!isInputEmpty()){
            authenticateUser();
        }
    }

    @FXML
    void selectBarangay(ActionEvent event) {
        login_iv_seal.setImage(barangays.get(login_cb_barangay.getValue()));
    }

    @FXML
    void enterKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)){
            login_pf_password.requestFocus();
            userRFID = login_tf_rfid.getText();
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
            login_tf_rfid.setEditable(true);
            login_tf_rfid.setText("");
            login_tf_rfid.requestFocus();

            login_tf_message.setText("Please scan your RFID.");
            login_btn_scan.setText("Cancel");

        } else { //reset back to default
            onCompleteCancelScan();
        }
    }

    public void start() throws FileNotFoundException {
        barangays = initBarangays();
        login_iv_seal.setImage(barangays.get("Guiwan"));
        login_tf_rfid.setEditable(false);
    }

    private HashMap<String, Image> initBarangays() throws FileNotFoundException {
        HashMap<String, Image> barangays = new HashMap<>();

        for (Barangay barangay: Barangay.values()){
            barangays.put(barangay.getBarangay(),
                    new Image(new FileInputStream(barangay.getFileReference()))
            );
            login_cb_barangay.getItems().add(barangay.getBarangay());
        }

        return barangays;
    }

    private boolean isInputEmpty() {
        if (login_tf_rfid.getText().isEmpty()){
            login_tf_message.setText("Please scan your RFID.");
            return true;

        } else if (login_pf_password.getText().isEmpty()){
            login_tf_message.setText("Please enter your password.");
            return true;

        } else if (login_cb_barangay.getValue() == null){
            login_tf_message.setText("Please select your barangay.");
            return true;

        } else {
            return false;
        }
    }

    private void authenticateUser() throws IOException {
        login_tf_message.setText("Logging in. Please wait.");

        String hashedPassword = DigestUtils.sha256Hex(login_pf_password.getText());

        if (RESTFacade.getInstance()
                .authenticateLogin(userRFID,
                        hashedPassword,
                        login_cb_barangay.getValue())){

            Admin.getInstance()
                    .setAdmin(RESTFacade.getInstance()
                            .getResident(
                                    login_cb_barangay.getValue(),
                                    userRFID));

            LoaderUtil.getLoaderInstance().showMain();

        } else {
            login_tf_message.setText("Incorrect Password or Wrong Barangay");
        }
    }

    private void onCompleteCancelScan() {
        isScanning = false;
        login_tf_rfid.setEditable(false);
        login_btn_scan.setText("Scan");
        login_tf_message.setText("");
        login_tf_rfid.setText(userRFID);
    }

}
