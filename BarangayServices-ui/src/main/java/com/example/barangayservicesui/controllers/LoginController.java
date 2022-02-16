package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.BarangayRESTClient;
import com.example.BarangayServicesclient.models.Admin;
import com.example.BarangayServicesclient.models.Resident;
import com.example.barangayservicesui.utils.AdminPreferences;
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
    private static final String FILE_REFERENCE = "src/main/resources/images/";

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
            matchRFIDandPassword();
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
                matchRFIDandPassword();
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
        initBarangays();
        login_iv_seal.setImage(barangays.get("Guiwan"));
        login_tf_rfid.setEditable(false);
    }

    private void initBarangays() throws FileNotFoundException {
        barangays = new HashMap<>();
        barangays.put("Tumaga",
                new Image(
                        new FileInputStream(FILE_REFERENCE +"Tumaga.png"))
        );
        barangays.put("Guiwan",
                new Image(
                        new FileInputStream(FILE_REFERENCE + "Guiwan.png")));
        barangays.put("StaMaria",
                new Image(new FileInputStream(FILE_REFERENCE + "StaMaria.png")));

        login_cb_barangay.getItems().addAll("Tumaga", "Guiwan", "StaMaria");
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

    private void matchRFIDandPassword() throws IOException {
        login_tf_message.setText("Logging in. Please wait.");

        Admin admin = BarangayRESTClient.getInstance().getLoginCreds(userRFID);
        String hashedPassword = DigestUtils.sha256Hex(login_pf_password.getText());

        if (hashedPassword.equals(admin.getPassword())
                && login_cb_barangay.getValue().equals(admin.getBarangay())){
            setAdminPreferences(
                    BarangayRESTClient.getInstance()
                            .getResident(login_cb_barangay.getValue(), userRFID));
            LoaderUtil.getLoaderInstance().showMain();

        } else if (!hashedPassword.equals(admin.getPassword())){
            login_tf_message.setText("Incorrect Password");

        } else if (!login_cb_barangay.getValue().equals(admin.getBarangay())){
            login_tf_message.setText("Wrong Barangay");
        }
    }

    private void setAdminPreferences(Resident adminDetails) {
        AdminPreferences.getPrefsInstance()
                .setAdmin(
                        adminDetails,
                        FILE_REFERENCE + adminDetails.getBarangay() + ".png"
                );
    }

    private void onCompleteCancelScan() {
        isScanning = false;
        login_tf_rfid.setEditable(false);
        login_btn_scan.setText("Scan");
        login_tf_message.setText("");
        login_tf_rfid.setText(userRFID);
    }

}
