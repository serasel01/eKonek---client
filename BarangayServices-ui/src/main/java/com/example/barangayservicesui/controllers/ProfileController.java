package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.BarangayRESTClient;
import com.example.BarangayServicesclient.Logging;
import com.example.BarangayServicesclient.models.Admin;
import com.example.BarangayServicesclient.models.Log;
import com.example.BarangayServicesclient.models.Resident;
import com.example.barangayservicesui.Main;
import com.example.barangayservicesui.utils.AdminPreferences;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.ExecutionException;

public class ProfileController {
    private static final String HOLDER_REFERENCE = "src/main/resources/images/UserPlate.png";

    private ToggleGroup rg_gender = new ToggleGroup();
    private boolean isEditMode = false;
    private boolean isUpdate = true;
    private Resident resident;
    private MainController mainController;

    @FXML
    private Button info_btn_cert;

    @FXML
    private Button info_btn_edit;

    @FXML
    private Button info_btn_remove;

    @FXML
    private Button info_btn_upload;

    @FXML
    private Button info_btn_scan;

    @FXML
    private Button info_btn_changePass;

    @FXML
    private ComboBox<String> info_cb_civil;

    @FXML
    private ComboBox<String> info_cb_eduAttain;

    @FXML
    private ComboBox<String> info_cb_status;

    @FXML
    private ComboBox<String> info_cb_type;

    @FXML
    private DatePicker info_dp_birthDay;

    @FXML
    private RadioButton info_rb_female;

    @FXML
    private RadioButton info_rb_male;

    @FXML
    private TextField info_tf_birthPlace;

    @FXML
    private TextField info_tf_emailAd;

    @FXML
    private TextField info_tf_firstName;

    @FXML
    private TextField info_tf_landline;

    @FXML
    private TextField info_tf_lastName;

    @FXML
    private TextField info_tf_lbp;

    @FXML
    private TextField info_tf_midName;

    @FXML
    private TextField info_tf_mobileNum;

    @FXML
    private TextField info_tf_occupation;

    @FXML
    private TextField info_tf_rfid;

    @FXML
    private TextField info_tf_street;

    @FXML
    private TextField info_tf_svz;

    @FXML
    private TextField info_tf_oldPass;

    @FXML
    private TextField info_tf_newPass;

    @FXML
    private TextField info_tf_newPass2;

    @FXML
    private Text info_tf_msg;

    @FXML
    private Text info_tf_msgPass;

    @FXML
    private ImageView info_iv_photo;

    @FXML
    void allowEditInfo(ActionEvent event)
            throws ExecutionException, InterruptedException, IOException {

        //if edit mode is not enabled, enable edit mode
        if (!isEditMode){
            setEditMode();

        //if edit mode is enabled, save and disable edit mode
        } else {
            if (!info_tf_rfid.getText().isEmpty() || !info_tf_firstName.getText().isEmpty() ||
                    !info_tf_midName.getText().isEmpty() || !info_tf_lastName.getText().isEmpty() ||
                    !info_tf_svz.getText().isEmpty() || !info_tf_birthPlace.getText().isEmpty()){

                if (isUpdate){
                    if (resident.getUserType().equals("Resident")
                            && !info_cb_type.getValue().equals("Resident") ){
                        createAdminAccount();

                    } else if (!resident.getUserType().equals("Resident")
                            && info_cb_type.getValue().equals("Resident")){
                        deleteAdminAccount();
                    }
                }

                getResidentInfo();
                unsetEditMode();
            }

        }
    }

    @FXML
    void scanRFID(ActionEvent event) {

    }

    @FXML
    void deleteResident(ActionEvent event) throws JsonProcessingException {
        BarangayRESTClient.getInstance()
                .deleteResident(resident.getBarangay(),
                        resident.getUserRFID());

        BarangayRESTClient.getInstance()
                .addLog(
                        AdminPreferences.getPrefsInstance().getUserBarangay(),
                        new Log(
                                AdminPreferences.getPrefsInstance().getUserRfid(),
                                resident.getUserRFID(),
                                AdminPreferences.getPrefsInstance().getName(),
                                resident.getFirstName() + " " +
                                        resident.getMiddleName() + " " + resident.getLastName(),
                                "Resident Account Deletion",
                                Instant.now().toEpochMilli()
                        )
                );
        mainController.viewManagePane();
    }

    @FXML
    void editPicture(ActionEvent event) throws IOException {
        openCameraDialog();
    }

    @FXML
    void changePassword(ActionEvent event) throws JsonProcessingException {
        Admin admin = BarangayRESTClient
                .getInstance()
                .getLoginCreds(
                        AdminPreferences
                        .getPrefsInstance()
                        .getUserRfid()
                );
        String hashedPassword = DigestUtils.sha256Hex(info_tf_oldPass.getText());


        if (info_tf_newPass.getText().equals(info_tf_newPass2.getText())
                && hashedPassword.equals(admin.getPassword())){

            admin.setPassword(
                    DigestUtils.sha256Hex(
                            info_tf_newPass.getText()
                    ));

            BarangayRESTClient
                    .getInstance()
                    .updateLoginCreds(
                            AdminPreferences.getPrefsInstance().getUserRfid(),
                            admin);

            info_tf_msgPass.setVisible(true);
            info_tf_msgPass.setText("Change Password Successfully!");

        } else if (!info_tf_newPass.getText().equals(info_tf_newPass2.getText())) {
            info_tf_msgPass.setVisible(true);
            info_tf_msgPass.setText("Passwords do not match");

        } else if (!hashedPassword.equals(admin.getPassword())){
            info_tf_msgPass.setVisible(true);
            info_tf_msgPass.setText("Wrong Current Password");
        }

    }

    private void openCameraDialog() throws IOException {
        //create new dialog
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/camera-view.fxml"));

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.getDialogPane().getChildren().add(loader.load());
        dialog.setResizable(true);
        dialog.getDialogPane().setMinSize(900, 690);

        CameraController cameraController = loader.getController();
        cameraController.setData(info_tf_rfid.getText(), dialog);

        dialog.show();
    }

    public void start(MainController mainController){
        this.mainController = mainController;

        info_cb_status.getItems().addAll("Alive", "Deceased");
        info_cb_civil.getItems().addAll("Single", "Married", "Divorced", "Separated", "Widowed");

        info_cb_eduAttain.getItems().addAll(
                "Elementary Undergraduate",
                "Elementary Graduate",
                "Junior High School Undergraduate",
                "Junior High School Graduate",
                "Senior High School Undergraduate",
                "Senior High School Graduate",
                "College Undergraduate",
                "College Graduate",
                "Master Undergraduate",
                "Master Degree",
                "Doctorate Undergraduate",
                "Doctorate Degree",
                "Vocational"
        );

        info_cb_type.getItems().addAll(
                "Resident",
                "Administrator",
                "Supervisor"
        );
    }

    public void initData(Resident resident) throws FileNotFoundException {
        this.resident = resident;
        initViews();
    }

    //function to display Resident details
    private void initViews() throws FileNotFoundException {
        info_cb_eduAttain.setValue(resident.getEducationalAttainment());
        info_cb_civil.setValue(resident.getCivilStatus());
        info_cb_status.setValue(resident.getStatus());
        info_cb_type.setValue(resident.getUserType());

        info_dp_birthDay.setValue(Instant
                .ofEpochMilli(resident.getBirthDate())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        );

        initRadioGroup(resident.getGender());

        info_tf_birthPlace.setText(resident.getBirthPlace());
        info_tf_emailAd.setText(resident.getEmailAddress());
        info_tf_firstName.setText(resident.getFirstName());
        info_tf_landline.setText(resident.getLandline());
        info_tf_lastName.setText(resident.getLastName());
        info_tf_lbp.setText(resident.getLotBlockPhase());
        info_tf_midName.setText(resident.getMiddleName());
        info_tf_mobileNum.setText(resident.getMobileNumber());
        info_tf_occupation.setText(resident.getOccupation());
        info_tf_rfid.setText(String.valueOf(resident.getUserRFID()));
        info_tf_street.setText(resident.getStreet());
        info_tf_svz.setText(resident.getSubdivisionVillageZone());
        info_btn_changePass.setVisible(true);

        try {
            info_iv_photo.setImage(new Image(new FileInputStream("src/main/resources/residentPhotos/" + resident.getUserRFID() + ".png")));
        } catch (Exception e){
            info_iv_photo.setImage(new Image(new FileInputStream("src/main/resources/images/UserPlate.png")));
        }

    }

    private void initRadioGroup(String gender) {
        info_rb_male.setToggleGroup(rg_gender);
        info_rb_female.setToggleGroup(rg_gender);

        if (gender.equals("Male")){
            info_rb_male.setSelected(true);
            info_rb_female.setSelected(false);
        } else {
            info_rb_female.setSelected(true);
            info_rb_male.setSelected(false);
        }
    }

    //enables Edit Mode
    private void setEditMode() {

        if (AdminPreferences.getPrefsInstance()
                .getUserType().equals("Supervisor")){
            info_cb_type.setEditable(true);
            info_cb_type.setDisable(false);
        }

        isEditMode = true;
        info_cb_civil.setEditable(true);
        info_tf_svz.setEditable(true);
        info_tf_street.setEditable(true);
        info_cb_eduAttain.setEditable(true);
        info_tf_rfid.setEditable(true);
        info_cb_status.setEditable(true);
        info_dp_birthDay.setEditable(true);
        info_rb_male.setDisable(false);
        info_tf_occupation.setEditable(true);
        info_tf_mobileNum.setEditable(true);
        info_tf_midName.setEditable(true);
        info_tf_lbp.setEditable(true);
        info_tf_lastName.setEditable(true);
        info_tf_landline.setEditable(true);
        info_tf_firstName.setEditable(true);
        info_tf_emailAd.setEditable(true);
        info_tf_birthPlace.setEditable(true);
        info_rb_female.setDisable(false);
        info_btn_scan.setDisable(false);

        info_btn_edit.setText("Save Information");
        info_btn_cert.setVisible(false);
        info_btn_remove.setVisible(false);
        info_btn_upload.setDisable(false);
        info_tf_msg.setVisible(true);
    }

    //disables Edit Mode
    private void unsetEditMode(){
        info_cb_type.setEditable(false);
        info_cb_type.setDisable(true);

        isEditMode = false;
        info_cb_civil.setEditable(false);
        info_tf_svz.setEditable(false);
        info_tf_street.setEditable(false);
        info_cb_eduAttain.setEditable(false);
        info_tf_rfid.setEditable(false);
        info_cb_status.setEditable(false);
        info_dp_birthDay.setEditable(false);
        info_rb_male.setDisable(true);
        info_tf_occupation.setEditable(false);
        info_tf_mobileNum.setEditable(false);
        info_tf_midName.setEditable(false);
        info_tf_lbp.setEditable(false);
        info_tf_lastName.setEditable(false);
        info_tf_landline.setEditable(false);
        info_tf_firstName.setEditable(false);
        info_tf_emailAd.setEditable(false);
        info_tf_birthPlace.setEditable(false);
        info_rb_female.setDisable(true);
        info_btn_scan.setDisable(true);

        info_btn_edit.setText("Edit Information");
        info_btn_cert.setVisible(true);
        info_btn_remove.setVisible(true);
        info_btn_upload.setDisable(true);
        info_tf_msg.setVisible(false);
    }

    private void getResidentInfo() throws ExecutionException, InterruptedException, IOException {
        resident.setEducationalAttainment(info_cb_eduAttain.getValue());
        resident.setCivilStatus(info_cb_civil.getValue());
        resident.setStatus(info_cb_status.getValue());
        resident.setUserType(info_cb_type.getValue());

        resident.setBirthDate(info_dp_birthDay
                .getValue()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        );

        if (info_rb_male.isSelected()){
            resident.setGender("Male");
        } else {
            resident.setStatus("Female");
        }

        resident.setBirthPlace(info_tf_birthPlace.getText());
        resident.setEmailAddress(info_tf_emailAd.getText());
        resident.setFirstName(info_tf_firstName.getText());
        resident.setLandline(info_tf_landline.getText());
        resident.setLastName(info_tf_lastName.getText());
        resident.setLotBlockPhase(info_tf_lbp.getText());
        resident.setMiddleName(info_tf_midName.getText());
        resident.setMobileNumber(info_tf_mobileNum.getText());
        resident.setOccupation(info_tf_occupation.getText());
        resident.setUserRFID(info_tf_rfid.getText());
        resident.setStreet(info_tf_street.getText());
        resident.setSubdivisionVillageZone(info_tf_svz.getText());
        resident.setBarangay(AdminPreferences.getPrefsInstance().getUserBarangay());

        if (isUpdate) {
            BarangayRESTClient.getInstance()
                    .updateResident(
                            AdminPreferences.getPrefsInstance().getUserBarangay(),
                            info_tf_rfid.getText(),
                            resident);

            BarangayRESTClient.getInstance()
                    .addLog(
                            AdminPreferences.getPrefsInstance().getUserBarangay(),
                            new Log(
                                    AdminPreferences.getPrefsInstance().getUserRfid(),
                                    resident.getUserRFID(),
                                    AdminPreferences.getPrefsInstance().getName(),
                                    resident.getFirstName() + " " +
                                            resident.getMiddleName() + " " + resident.getLastName(),
                                    "Update Resident Info",
                                    Instant.now().toEpochMilli()
                            )
                    );

        } else {
            unsetEditMode();
            //show Resident Added Success
            Logging.printInfoLog(
                    BarangayRESTClient.getInstance()
                            .addResident(
                                    AdminPreferences.getPrefsInstance().getUserBarangay(),
                                    info_tf_rfid.getText(),
                                    resident
                            )
            );

            BarangayRESTClient.getInstance()
                    .addLog(
                            AdminPreferences.getPrefsInstance().getUserBarangay(),
                            new Log(
                                    AdminPreferences.getPrefsInstance().getUserRfid(),
                                    resident.getUserRFID(),
                                    AdminPreferences.getPrefsInstance().getName(),
                                    resident.getFirstName() + " " +
                                            resident.getMiddleName() + " " + resident.getLastName(),
                                    "Resident Account Creation",
                                    Instant.now().toEpochMilli()
                            )
                    );
        }
    }

    private void createAdminAccount() throws IOException,
            ExecutionException, InterruptedException {

            String hashedPassword = DigestUtils.sha256Hex("password");

            Admin admin = new Admin();
            admin.setUserRFID(info_tf_rfid.getText());
            admin.setBarangay(AdminPreferences.getPrefsInstance().getUserBarangay());
            admin.setPassword(hashedPassword);

            BarangayRESTClient.getInstance()
                    .addLoginCreds(
                            info_tf_rfid.getText(), admin);

        BarangayRESTClient.getInstance()
                .addLog(
                        AdminPreferences.getPrefsInstance().getUserBarangay(),
                        new Log(
                                AdminPreferences.getPrefsInstance().getUserRfid(),
                                resident.getUserRFID(),
                                AdminPreferences.getPrefsInstance().getName(),
                                resident.getFirstName() + " " +
                                        resident.getMiddleName() + " " + resident.getLastName(),
                                "Admin Account Creation",
                                Instant.now().toEpochMilli()
                        )
                );

    }

    private void deleteAdminAccount() throws JsonProcessingException {
        BarangayRESTClient.getInstance()
                .deleteLoginCreds(info_tf_rfid.getText());

        BarangayRESTClient.getInstance()
                .addLog(
                        AdminPreferences.getPrefsInstance().getUserBarangay(),
                        new Log(
                                AdminPreferences.getPrefsInstance().getUserRfid(),
                                resident.getUserRFID(),
                                AdminPreferences.getPrefsInstance().getName(),
                                resident.getFirstName() + " " +
                                        resident.getMiddleName() + " " + resident.getLastName(),
                                "Admin Account Deletion",
                                Instant.now().toEpochMilli()
                        )
                );
    }

    public void clearData() throws FileNotFoundException {
        info_btn_changePass.setVisible(false);
        info_cb_eduAttain.setValue("");
        info_cb_civil.setValue("");
        info_cb_status.setValue("");

        info_dp_birthDay.setValue(LocalDate.now());

        info_rb_male.setSelected(false);
        info_rb_female.setSelected(false);

        info_tf_birthPlace.setText("");
        info_tf_emailAd.setText("");
        info_tf_firstName.setText("");
        info_tf_landline.setText("");
        info_tf_lastName.setText("");
        info_tf_lbp.setText("");
        info_tf_midName.setText("");
        info_tf_mobileNum.setText("");
        info_tf_occupation.setText("");
        info_tf_rfid.setText("");
        info_tf_street.setText("");
        info_tf_svz.setText("");

        info_iv_photo.setImage(new Image(new FileInputStream(HOLDER_REFERENCE)));
        isUpdate = false;
        resident = new Resident();
        setEditMode();

        info_cb_type.setValue("Resident");
        info_cb_type.setEditable(false);
        info_cb_type.setDisable(true);

    }

}
