package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.RESTFacade;
import com.example.BarangayServicesclient.models.Case;
import com.example.BarangayServicesclient.models.Log;
import com.example.BarangayServicesclient.models.Resident;
import com.example.barangayservicesui.Main;
import com.example.barangayservicesui.certificates.Certificate;
import com.example.barangayservicesui.certificates.CertificateFactory;
import com.example.barangayservicesui.enums.*;
import com.example.barangayservicesui.utils.Admin;
import com.example.barangayservicesui.utils.AlertManager;
import com.example.barangayservicesui.utils.LoaderUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfileController {
    private static final String HOLDER_REFERENCE = "src/main/resources/images/UserPlate.png";

    private ToggleGroup rg_gender = new ToggleGroup();
    private boolean isEditMode = false;
    private boolean isUpdate = true;
    private Resident resident;
    private MainController mainController;
    private String userRFID;
    private boolean isScanning = false;

    @FXML
    private Button info_btn_addCase;

    @FXML
    private Button info_btn_cert;

    @FXML
    private Button info_btn_changePass;

    @FXML
    private Button info_btn_edit;

    @FXML
    private Button info_btn_remove;

    @FXML
    private Button info_btn_scan;

    @FXML
    private Button info_btn_upload;

    @FXML
    private Button info_btn_directory;

    @FXML
    private ComboBox<String> info_cb_certType;

    @FXML
    private ComboBox<String> info_cb_civil;

    @FXML
    private ComboBox<String> info_cb_eduAttain;

    @FXML
    private ComboBox<String> info_cb_status;

    @FXML
    private ComboBox<String> info_cb_userType;

    @FXML
    private DatePicker info_dp_birthDay;

    @FXML
    private DatePicker info_dp_dateFilled;

    @FXML
    private ImageView info_iv_photo;

    @FXML
    private RadioButton info_rb_female;

    @FXML
    private RadioButton info_rb_male;

    @FXML
    private TextField info_tf_birthPlace;

    @FXML
    private TextField info_tf_caseId;

    @FXML
    private TextField info_tf_caseName;

    @FXML
    private Text info_tf_cert;

    @FXML
    private Text info_tf_changePass;

    @FXML
    private TextField info_tf_description;

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
    private Text info_tf_msg;

    @FXML
    private Text info_tf_msgPass;

    @FXML
    private TextField info_tf_newPass;

    @FXML
    private TextField info_tf_newPass2;

    @FXML
    private TextField info_tf_occupation;

    @FXML
    private TextField info_tf_oldPass;

    @FXML
    private TextField info_tf_rfid;

    @FXML
    private TextField info_tf_street;

    @FXML
    private TextField info_tf_svz;

    @FXML
    private TableView<Case> info_tv_cases;

    @FXML
    void addCase(ActionEvent event)
            throws JsonProcessingException {

        if (new AlertManager(Alert.AlertType.CONFIRMATION)
                .setMessage("You are going to add a case for this resident. " +
                        "Adding a case to a resident is an irreversible process. " +
                        "Once added to the record, YOU CANNOT DELETE THE CASE. " +
                        "Are you sure to proceed adding the case? ")
                .showAndWait()){

            Admin.getInstance()
                    .addResidentCase(
                            resident,
                            new Case(info_tf_caseId.getText(),
                                    info_tf_caseName.getText(),
                                    info_dp_dateFilled
                                            .getValue()
                                            .format(DateTimeFormatter
                                                    .ofPattern("LLLL dd, yyyy")),
                                    info_tf_description.getText())
                    );

            new AlertManager(Alert.AlertType.INFORMATION)
                    .setMessage("Case Added Sucessfully")
                    .show();

            displayCases(loadCases());
        }
    }

    @FXML
    void allowEditInfo(ActionEvent event)
            throws ExecutionException, InterruptedException, IOException {

        //if edit mode is not enabled, enable edit mode
        if (!isEditMode){
            isUpdate = true;
            setEditMode();

        //if edit mode is enabled, save and disable edit mode
        } else {
            if (    !info_tf_rfid.getText().isEmpty() ||
                    !info_tf_firstName.getText().isEmpty() ||
                    !info_tf_midName.getText().isEmpty() ||
                    !info_tf_lastName.getText().isEmpty()){

                if (isUpdate){

                    if (resident.getUserType().equals(UserType.Resident.name()) &&
                            !info_cb_userType.getValue().equals(UserType.Resident.name())){

                        if (new AlertManager(Alert.AlertType.CONFIRMATION)
                                .setMessage("Updating to Admin / Supervisor " +
                                        "will create an Admin account. Are you sure " +
                                        "you want to update? ")
                                .showAndWait()){

                            createAdminAccount();
                            resident.setUserType(info_cb_userType.getValue());
                        }

                    } else if (!resident.getUserType().equals(UserType.Resident.name()) &&
                            info_cb_userType.getValue().equals(UserType.Resident.name())){

                        if (new AlertManager(Alert.AlertType.CONFIRMATION)
                                .setMessage("Updating to Resident " +
                                        "will delete the Admin account. Are you sure " +
                                        "you want to update? ")
                                .showAndWait()){

                            deleteAdminAccount();
                            resident.setUserType(info_cb_userType.getValue());
                        }

                    }
                }

                getResidentInfo();
                unsetEditMode();
            }
        }
    }

    @FXML
    void scanRFID(ActionEvent event) {
        if (!isScanning){ //change to scanning. waits for input from reader
            isScanning = true;
            info_tf_rfid.setText("");
            info_tf_rfid.requestFocus();
            info_btn_scan.setText("Cancel");

        } else { //reset back to default
            onCompleteCancelScan();
        }
    }

    @FXML
    void enterKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)){
            onCompleteCancelScan();
        }
    }

    @FXML
    void deleteResident(ActionEvent event)
            throws JsonProcessingException {

        if (!Admin.getInstance()
                .getAdmin()
                .getUserRFID()
                .equals(userRFID)){

            if (new AlertManager(Alert.AlertType.CONFIRMATION)
                    .setMessage("Are you sure to remove this resident? " +
                            "Once deleted, the resident's information CANNOT BE RECOVERED.")
                    .showAndWait()){

                Admin.getInstance().getResidentMap().remove(resident.getUserRFID());
                Admin.getInstance()
                        .deleteResident(resident);
            }

        } else {
            new AlertManager(Alert.AlertType.ERROR)
                    .setMessage("Invalid Action: You cannot delete your own profile.")
                    .show();
        }

        mainController.viewManagePane();
    }

    @FXML
    void editPicture(ActionEvent event)
            throws IOException {
        openCameraDialog();
    }

    @FXML
    void changePassword(ActionEvent event)
            throws JsonProcessingException {

        com.example.BarangayServicesclient.models.Admin admin
                = RESTFacade.getInstance()
                .getLoginCreds(
                        Admin.getInstance()
                                .getAdmin()
                                .getUserRFID()
                );

        String hashedPassword = DigestUtils
                .sha256Hex(info_tf_oldPass.getText());

        if (info_tf_newPass.getText().equals(info_tf_newPass2.getText())
                && hashedPassword.equals(admin.getPassword())){

            admin.setPassword(
                    DigestUtils.sha256Hex(
                            info_tf_newPass.getText()
                    ));

            RESTFacade
                    .getInstance()
                    .updateLoginCreds(
                            Admin.getInstance().getAdmin().getUserRFID(),
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

    @FXML
    void generateCertificate(ActionEvent event)
            throws IOException {

        Certificate certificate = new CertificateFactory()
                .getCertificate(info_cb_certType.getValue(), resident);
        certificate.createCertificate(certificate.mapDocContent(),
                certificate.getDocument());
        certificate.saveCertificate(certificate.getDocument());

        Admin.getInstance()
                .addLog(resident,
                        LogEvent.CertificateIssuance.getEvent()
                );

        new AlertManager(Alert.AlertType.INFORMATION)
                .setMessage("Certificate Created Successfully")
                .show();
    }

    @FXML
    void openDirectory(ActionEvent event) throws IOException {
        Desktop.getDesktop()
                .open(new File("src/main/resources/CreatedCertificates"));
    }

    public void start(MainController mainController){
        this.mainController = mainController;
        initViews();
    }

    private void initViews() {
        info_cb_status.getItems()
                .addAll(Status.Alive.name(),
                        Status.Deceased.name());

        info_cb_civil.getItems()
                .addAll(CivilStatus.Single.name(),
                        CivilStatus.Married.name(),
                        CivilStatus.Separated.name(),
                        CivilStatus.Divorced.name(),
                        CivilStatus.Widowed.name());

        info_cb_eduAttain.getItems().addAll(
                EducationalAttainment.ElementaryUndergraduate.getEducationalAttainment(),
                EducationalAttainment.ElementaryGraduate.getEducationalAttainment(),
                EducationalAttainment.JuniorHighSchoolUndergraduate.getEducationalAttainment(),
                EducationalAttainment.JuniorHighSchoolGraduate.getEducationalAttainment(),
                EducationalAttainment.SeniorHighSchoolUndergraduate.getEducationalAttainment(),
                EducationalAttainment.SeniorHighSchoolGraduate.getEducationalAttainment(),
                EducationalAttainment.CollegeUndergraduate.getEducationalAttainment(),
                EducationalAttainment.CollegeGraduate.getEducationalAttainment(),
                EducationalAttainment.MasterUndergraduate.getEducationalAttainment(),
                EducationalAttainment.MasterDegree.getEducationalAttainment(),
                EducationalAttainment.DoctorateUndergraduate.getEducationalAttainment(),
                EducationalAttainment.DoctorateDegree.getEducationalAttainment(),
                EducationalAttainment.Vocational.getEducationalAttainment());

        info_cb_userType.getItems().addAll(
                UserType.Resident.name(),
                UserType.Administrator.name()
        );

        info_cb_certType.getItems()
                .addAll(
                        Admin.getInstance()
                                .getBarangay()
                                .getCertificateTypes()
        );

        initCasesTable();
    }

    public void initData(Resident resident) throws FileNotFoundException {
        this.resident = resident;
        userRFID = resident.getUserRFID();
        setValuesToViews();
    }

    //function to display Resident details
    private void setValuesToViews() throws FileNotFoundException {
        info_cb_eduAttain.setValue(resident.getEducationalAttainment());
        info_cb_civil.setValue(resident.getCivilStatus());
        info_cb_status.setValue(resident.getStatus());
        info_cb_userType.setValue(resident.getUserType());

        info_dp_birthDay.setValue(Instant
                .ofEpochMilli(resident.getBirthDate())
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        );

        setValueToRadioGroup(resident.getGender());

        info_tf_birthPlace.setText(resident.getBirthPlace());
        info_tf_emailAd.setText(resident.getEmailAddress());
        info_tf_firstName.setText(resident.getFirstName());
        info_tf_landline.setText(resident.getLandline());
        info_tf_lastName.setText(resident.getLastName());
        info_tf_lbp.setText(resident.getLotBlockPhase());
        info_tf_midName.setText(resident.getMiddleName());
        info_tf_mobileNum.setText(resident.getMobileNumber());
        info_tf_occupation.setText(resident.getOccupation());
        info_tf_rfid.setText(resident.getUserRFID());
        info_tf_street.setText(resident.getStreet());
        info_tf_svz.setText(resident.getSubdivisionVillageZone());
        info_btn_changePass.setVisible(true);

        try {
            info_iv_photo.setImage(new Image(
                    new FileInputStream("src/main/resources/residentPhotos/"
                    + resident.getUserRFID() + ".png")));

        } catch (Exception e){
            info_iv_photo.setImage(new Image(
                    new FileInputStream("src/main/resources/images/UserPlate.png")));
        }

        displayCases(loadCases());
    }

    private void initCasesTable() {
        TableColumn caseIdCol = new TableColumn("Case ID");
        caseIdCol.setCellValueFactory(
                new PropertyValueFactory<Log, String>("caseId")
        );

        TableColumn caseNameCol = new TableColumn("Case Name");
        caseNameCol.setCellValueFactory(
                new PropertyValueFactory<Log, String>("caseName")
        );

        TableColumn dateCol = new TableColumn("Date Filled");
        dateCol.setCellValueFactory(
                new PropertyValueFactory<Log, String>("dateFilled")
        );

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<Log, String>("description")
        );

        info_tv_cases.getColumns()
                .setAll(
                        caseIdCol,
                        caseNameCol,
                        dateCol,
                        descriptionCol
                );
    }

    private void displayCases(ObservableList<Case> cases) {
        info_tv_cases.getItems().clear();
        info_tv_cases.setItems(cases);
    }
    private ObservableList<Case> loadCases() {
        return FXCollections.observableList(
                RESTFacade.getInstance()
                        .getCases(resident.getBarangay(),
                                resident.getUserRFID())
        );
    }

    private void setValueToRadioGroup(String gender) {
        info_rb_male.setToggleGroup(rg_gender);
        info_rb_female.setToggleGroup(rg_gender);

        if (gender.equals("Male")){
            info_rb_male.setSelected(true);
            info_rb_female.setSelected(false);

        } else if (gender.equals("Female")){
            info_rb_female.setSelected(true);
            info_rb_male.setSelected(false);
        }
    }

    //this is for saving resident info
    private void getResidentInfo()
            throws ExecutionException, InterruptedException, IOException {

        Resident.ResidentBuilder builder = new Resident.ResidentBuilder(
                info_tf_firstName.getText(),
                info_tf_midName.getText(),
                info_tf_lastName.getText(),
                getValueFromRG(),
                info_tf_birthPlace.getText(),
                Admin.getInstance().getAdmin().getBarangay(),
                info_cb_civil.getValue(),
                info_cb_eduAttain.getValue(),
                info_cb_status.getValue(),
                info_cb_userType.getValue(),
                info_tf_rfid.getText(),

                info_dp_birthDay
                        .getValue()
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli()
        );

        builder.setEmailAddress(info_tf_emailAd.getText());
        builder.setLandline(info_tf_landline.getText());
        builder.setLotBlockPhase(info_tf_lbp.getText());
        builder.setMobileNumber(info_tf_mobileNum.getText());
        builder.setOccupation(info_tf_occupation.getText());
        builder.setStreet(info_tf_street.getText());
        builder.setSubdivisionVillageZone(info_tf_svz.getText());
        this.resident = builder.build();

        if (isUpdate) {
            saveUpdatedInfo();
        } else {
            saveAddedResident();
        }
    }

    private void saveAddedResident() throws JsonProcessingException {
        unsetEditMode();

        Admin.getInstance().addResident(resident);
        Admin.getInstance().addLog(
                resident,
                LogEvent.ResidentAccountCreation.getEvent());

        //show Resident Added Success
        new AlertManager(Alert.AlertType.INFORMATION)
                .setMessage("Resident Added Successfully")
                .show();

        mainController.viewManagePane();
    }

    private void saveUpdatedInfo()
            throws JsonProcessingException, FileNotFoundException {
        if (Admin.getInstance()
                .getAdmin()
                .getUserRFID()
                .equals(resident.getUserRFID())){

            Admin.getInstance().setAdmin(resident);
            mainController.initAdmin();
        }

        //runs if new RFID
        if (!userRFID.equals(resident.getUserRFID())){
            //get Cases first
            List<Case> caseList = RESTFacade.getInstance()
                    .getCases(
                            resident.getBarangay(),
                            userRFID);

            //delete resident record
            Admin.getInstance()
                    .deleteResident(new Resident(
                            resident.getFirstName(),
                            resident.getMiddleName(),
                            resident.getLastName(),
                            resident.getBarangay(),
                            resident.getUserRFID()
                    ));

            //add resident record with new RFID entry
            Admin.getInstance().addResident(resident);

            //add cases
            for (Case aCase : caseList){
                RESTFacade.getInstance().addCase(resident, aCase);
            }

            if (!resident.getUserType()
                    .equals(UserType.Resident.name())){
                //get Login creds first
                com.example.BarangayServicesclient.models.Admin admin =
                        RESTFacade.getInstance().getLoginCreds(userRFID);

                //add Login creds with new RFID entry
                RESTFacade.getInstance().addLoginCreds(resident.getUserRFID(), admin);
                RESTFacade.getInstance().deleteLoginCreds(userRFID);
            }

            //if same RFID
        } else {
            Admin.getInstance().editResidentInfo(resident);
            initData(resident);
        }

        Admin.getInstance().addLog(
                resident,
                LogEvent.UpdateResidentInfo.getEvent());

        new AlertManager(Alert.AlertType.INFORMATION)
                .setMessage("Resident Updated Successfully")
                .show();
    }

    private String getValueFromRG() {
        if (info_rb_male.isSelected()){
            return "Male";
        } else if (info_rb_female.isSelected()){
            return "Female";
        } else {
            return null;
        }
    }

    private void createAdminAccount()
            throws IOException, ExecutionException, InterruptedException {

        String hashedPassword = DigestUtils.sha256Hex("password");

        com.example.BarangayServicesclient.models.Admin admin =
                new com.example.BarangayServicesclient.models.Admin();
        admin.setUserRFID(info_tf_rfid.getText());
        admin.setBarangay(Admin.getInstance().getAdmin().getBarangay());
        admin.setPassword(hashedPassword);

        RESTFacade.getInstance()
                .addLoginCreds(
                        info_tf_rfid.getText(),
                        admin);

        Admin.getInstance()
                .addLog(resident,
                LogEvent.AdminAccountCreation.getEvent());
    }

    private void deleteAdminAccount() throws JsonProcessingException {
        RESTFacade.getInstance()
                .deleteLoginCreds(info_tf_rfid.getText());

        Admin.getInstance()
                .addLog(resident,
                        LogEvent.AdminAccountDeletion.getEvent());
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

        info_cb_userType.setValue("Resident");
        info_cb_userType.setEditable(false);
        info_cb_userType.setDisable(true);
        info_tv_cases.getItems().clear();

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

    //enables Edit Mode
    public void setEditMode() {
        info_cb_userType.setEditable(true);
        info_cb_userType.setDisable(false);

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
        info_cb_certType.setVisible(false);

        info_tf_oldPass.setVisible(false);
        info_tf_newPass.setVisible(false);
        info_tf_newPass2.setVisible(false);
        info_btn_changePass.setVisible(false);

        info_tf_cert.setVisible(false);
        info_tf_changePass.setVisible(false);
    }

    //disables Edit Mode
    private void unsetEditMode(){
        info_cb_userType.setEditable(false);
        info_cb_userType.setDisable(true);

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
        info_cb_certType.setVisible(true);

        info_tf_oldPass.setVisible(true);
        info_tf_newPass.setVisible(true);
        info_tf_newPass2.setVisible(true);
        info_btn_changePass.setVisible(true);

        info_tf_cert.setVisible(true);
        info_tf_changePass.setVisible(true);
    }

    private void onCompleteCancelScan() {
        isScanning = false;
        info_btn_scan.setText("Scan");
    }

}
