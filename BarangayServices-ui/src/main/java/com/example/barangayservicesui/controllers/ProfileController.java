package com.example.barangayservicesui.controllers;

import com.example.BarangayServicesclient.DatabaseFacade;
import com.example.BarangayServicesclient.Logging;
import com.example.BarangayServicesclient.enums.OfficialFilterParameter;
import com.example.BarangayServicesclient.enums.ResidentFilterParameter;
import com.example.BarangayServicesclient.models.Case;
import com.example.BarangayServicesclient.models.Official;
import com.example.BarangayServicesclient.models.Resident;
import com.example.barangayservicesui.enums.CivilStatus;
import com.example.barangayservicesui.enums.EducationalAttainment;
import com.example.barangayservicesui.enums.LogEvent;
import com.example.barangayservicesui.utils.AlertManager;
import com.example.barangayservicesui.utils.LoaderUtil;
import com.example.barangayservicesui.utils.TextFileReader;
import com.example.barangayservicesui.utils.Admin;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProfileController {
    private MainController mainController;
    private Resident resident;
    private boolean isScanning = false;
    private boolean isEditMode = false;
    private boolean isUpdate = false;

    @FXML
    private Button btnCases;

    @FXML
    private Button btnEditInfo;

    @FXML
    private Button btnEditPic;

    @FXML
    private Button btnRemoveResident;

    @FXML
    private Button btnScan;

    @FXML
    private Button btnSettings;

    @FXML
    private ComboBox<String> cbBirthplace;

    @FXML
    private ComboBox<String> cbBlood;

    @FXML
    private ComboBox<String> cbCivil;

    @FXML
    private ComboBox<String> cbEducation;

    @FXML
    private ComboBox<String> cbFatherBirthplace;

    @FXML
    private ComboBox<String> cbNationality;

    @FXML
    private ComboBox<String> cbOccupation;

    @FXML
    private ComboBox<String> cbPWD;

    @FXML
    private ComboBox<String> cbQualifier;

    @FXML
    private ComboBox<String> cbReligion;

    @FXML
    private ComboBox<String> cbSex;

    @FXML
    private ComboBox<String> cbSocial;

    @FXML
    private ComboBox<String> cbFatherQualifier;

    @FXML
    private ComboBox<String> cbMotherBirthplace;

    @FXML
    private ComboBox<String> cbMotherQualifier;

    @FXML
    private ComboBox<String> cbSpouseQualifier;

    @FXML
    private DatePicker dpBirthdate;

    @FXML
    private ImageView ivPhoto;

    @FXML
    private TextField tfBarangay;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfFatherFirst;

    @FXML
    private TextField tfFatherLast;

    @FXML
    private TextField tfFatherMiddle;

    @FXML
    private TextField tfFatherOtherCountry;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfHeight;

    @FXML
    private TextField tfLandline;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfMiddle;

    @FXML
    private TextField tfMobile;

    @FXML
    private TextField tfMotherFirst;

    @FXML
    private TextField tfMotherLast;

    @FXML
    private TextField tfMotherMiddle;

    @FXML
    private TextField tfMotherOtherCountry;

    @FXML
    private TextField tfOther;

    @FXML
    private TextField tfRFID;

    @FXML
    private TextField tfSpouseFirst;

    @FXML
    private TextField tfSpouseLast;

    @FXML
    private TextField tfSpouseMiddle;

    @FXML
    private TextField tfStreet;

    @FXML
    private TextField tfVotersID;

    @FXML
    private TextField tfWeight;

    @FXML
    void allowEditInfo(ActionEvent event)
            throws IOException {

        if (!isEditMode){
            Logging.printInfoLog("toggle edit");
            toggleEditMode();
            isUpdate = true;

        } else {
            Resident newResident = buildResident();

            if (isUpdate){

                if (new AlertManager(Alert.AlertType.CONFIRMATION)
                        .setMessage("Are you sure to update this resident's information?")
                        .showAndWait()){
                    saveUpdatedInfo(newResident);
                }

            } else {

                //check for existing profile
                if (!isProfileExisting(newResident)){

                    if (new AlertManager(Alert.AlertType.CONFIRMATION)
                            .setMessage("Are you sure to add this resident?")
                            .showAndWait()) {

                        saveAddedResident(newResident);
                        unToggleEditMode();
                    }


                } else {
                    new AlertManager(Alert.AlertType.ERROR)
                            .setMessage("ERROR: This resident's profile already exists in the system.")
                            .show();
                }

            }
        }
    }

    @FXML
    void deleteResident(ActionEvent event) throws IOException {

        List<Official> officials = DatabaseFacade
                .getInstance()
                .getOfficials(
                        resident.getBarangay(),
                        OfficialFilterParameter.OfficialRFID,
                        resident.getUserRFID());

        //guard check to make sure admin does not delete itself
        if (!Admin.getInstance()
                .getAdmin()
                .getUserRFID()
                .equals(resident.getUserRFID())){

            if (officials.isEmpty()){

                if (new AlertManager(Alert.AlertType.CONFIRMATION)
                        .setMessage("Are you sure to remove this resident? " +
                                "Once deleted, the resident's information CANNOT BE RECOVERED.")
                        .showAndWait()){

                    Admin.getInstance()
                            .getResidentMap()
                            .remove(resident.getUserRFID());

                    Admin.getInstance()
                            .deleteResident(resident);

                    Admin.getInstance()
                            .addLog(LogEvent.ResidentAccountDeletion.getEvent());

                    mainController.switchToResidents(null);
                }

            } else {
                new AlertManager(Alert.AlertType.ERROR)
                        .setMessage("Invalid Action: You cannot delete this resident because he/she " +
                                    "is a registered user. Delete the resident's official account first.")
                        .show();
                }

        } else {
            new AlertManager(Alert.AlertType.ERROR)
                    .setMessage("Invalid Action: You cannot delete your own profile.")
                    .show();
        }

    }

    @FXML
    void editPicture(ActionEvent event) throws IOException {
        LoaderUtil.getLoaderInstance()
                .loadCamera(tfRFID.getText(), this);
    }

    @FXML
    void enterKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)){
            onCompleteCancelScan();
        }
    }

    @FXML
    void openCases(ActionEvent event) throws IOException {
        LoaderUtil.getLoaderInstance()
                .loadCases(resident);
    }

    @FXML
    void openSettings(ActionEvent event) throws IOException {
        LoaderUtil.getLoaderInstance().loadSettings();
    }

    @FXML
    void scanRFID(ActionEvent event) {
        if (!isScanning){ //change to scanning. waits for input from reader
            isScanning = true;
            tfRFID.setText("");
            tfRFID.requestFocus();
            btnScan.setText("Cancel");

        } else { //reset back to default
            onCompleteCancelScan();
        }
    }

    public void start(MainController mainController)
            throws FileNotFoundException {

        this.mainController = mainController;
        isEditMode = true;
        initViews();

        setRestrictions();
    }

    private void setRestrictions() {
        if (Admin.getInstance()
                .getOfficial()
                .getUserType()
                .equals("Secretary")){

            btnRemoveResident.setVisible(false);
            btnRemoveResident.setDisable(true);

        } else if (Admin.getInstance()
                .getOfficial()
                .getUserType()
                .equals("Official")){

            btnRemoveResident.setVisible(false);
            btnRemoveResident.setDisable(true);
            btnEditInfo.setVisible(false);
            btnEditInfo.setDisable(true);

        }
    }

    public void initData(Resident resident)
            throws FileNotFoundException {

        this.resident = resident;
        setValuesToViews();

        if (!Admin.getInstance().getAdmin().getUserRFID().equals(
                this.resident.getUserRFID())){
            hideAccountSettingsButton();
        }
    }

    private void setValuesToViews() throws FileNotFoundException {
        tfRFID.setText(resident.getUserRFID());
        tfFirstName.setText(resident.getFirstName());
        tfMiddle.setText(resident.getMiddleName());
        tfLastName.setText(resident.getLastName());
        cbQualifier.setValue(resident.getQualifier());
        cbSex.setValue(resident.getGender());
        cbCivil.setValue(resident.getCivilStatus());
        tfVotersID.setText(resident.getVotersID());
        cbNationality.setValue(resident.getNationality());
        cbBirthplace.setValue(resident.getBirthPlace());
        dpBirthdate.setValue(LocalDate.parse(resident.getBirthDate(),
                DateTimeFormatter.ofPattern("LLLL dd, yyyy")));

        if (resident.isPWD())
            cbPWD.setValue("Yes");
        else
            cbPWD.setValue("No");

        tfStreet.setText(resident.getHouseBuildingStreet());
        tfBarangay.setText(resident.getBarangay());

        tfEmail.setText(resident.getEmailAddress());
        tfMobile.setText(resident.getMobileNumber());
        tfLandline.setText(resident.getLandline());

        cbReligion.setValue(resident.getReligion());
        cbEducation.setValue(resident.getEducationalAttainment());
        cbOccupation.setValue(resident.getOccupation());
        cbSocial.setValue(resident.getSocialClass());
        cbBlood.setValue(resident.getBloodType());
        tfHeight.setText(String.valueOf(resident.getHeight()));
        tfWeight.setText(String.valueOf(resident.getWeight()));

        tfFatherFirst.setText(resident.getFatherFirstName());
        tfFatherMiddle.setText(resident.getFatherMiddleName());
        tfFatherLast.setText(resident.getFatherLastName());
        cbFatherQualifier.setValue(resident.getFatherQualifier());
        cbFatherBirthplace.setValue(resident.getFatherBirthPlace());

        tfMotherFirst.setText(resident.getMotherFirstName());
        tfMotherMiddle.setText(resident.getMotherMiddleName());
        tfMotherLast.setText(resident.getMotherLastName());
        cbMotherQualifier.setValue(resident.getMotherQualifier());
        cbMotherBirthplace.setValue(resident.getMotherBirthPlace());

        tfSpouseFirst.setText(resident.getSpouseFirstName());
        tfSpouseMiddle.setText(resident.getSpouseMiddleName());
        tfSpouseLast.setText(resident.getSpouseLastName());
        cbSpouseQualifier.setValue(resident.getSpouseQualifier());

        loadPhoto();

        unToggleEditMode();
    }

    public void loadPhoto() throws FileNotFoundException {
        try {
            ivPhoto.setImage(new Image(
                    new FileInputStream("src/main/resources/residentPhotos/"
                            + tfRFID.getText() + ".png")));

        } catch (Exception e){
            ivPhoto.setImage(new Image(
                    new FileInputStream("src/main/resources/images/UserPlate.png")));
        }
    }

    private void initViews() {
        cbCivil.getItems()
                .addAll(
                        CivilStatus.Single.name(),
                        CivilStatus.Married.name(),
                        CivilStatus.Separated.name(),
                        CivilStatus.Divorced.name(),
                        CivilStatus.Widowed.name(),
                        CivilStatus.Annulled.name()
                );

        cbEducation.getItems()
                .addAll(
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
                EducationalAttainment.Vocational.getEducationalAttainment()
                );

        cbSex.getItems()
                .addAll(
                "Male", "Female"
        );

        cbPWD.getItems()
                .addAll(
                "Yes", "No"
        );

        cbQualifier.getItems()
                .addAll(
                new TextFileReader()
                        .readFileSingleLine("src/main/resources/textfiles/qualifier.txt")
                        .getStringArrayDataComma()
        );

        cbMotherQualifier.getItems()
                .addAll(
                new TextFileReader()
                        .readFileSingleLine("src/main/resources/textfiles/qualifier.txt")
                        .getStringArrayDataComma()
        );

        cbFatherQualifier.getItems()
                .addAll(
                new TextFileReader()
                        .readFileSingleLine("src/main/resources/textfiles/qualifier.txt")
                        .getStringArrayDataComma()
        );

        cbSpouseQualifier.getItems()
                .addAll(
                new TextFileReader()
                        .readFileSingleLine("src/main/resources/textfiles/qualifier.txt")
                        .getStringArrayDataComma()
        );

        cbNationality.getItems()
                .addAll(
                new TextFileReader()
                        .readFileSingleLine("src/main/resources/textfiles/nationalities.txt")
                        .getStringArrayDataComma()
        );

        cbBirthplace.getItems()
                .addAll(
                new TextFileReader()
                        .readFileMultipleLines("src/main/resources/textfiles/birthplace.txt")
        );

        cbFatherBirthplace.getItems()
                .addAll(
                new TextFileReader()
                        .readFileMultipleLines("src/main/resources/textfiles/birthplace.txt")
        );

        cbMotherBirthplace.getItems()
                .addAll(
                new TextFileReader()
                        .readFileMultipleLines("src/main/resources/textfiles/birthplace.txt")
        );

        cbBlood.getItems()
                .addAll(
                new TextFileReader()
                        .readFileSingleLine("src/main/resources/textfiles/bloodtype.txt")
                        .getStringArrayDataComma()
        );

        cbOccupation.getItems()
                .addAll(
                new TextFileReader()
                        .readFileSingleLine("src/main/resources/textfiles/occupation.txt")
                        .getStringArrayDataComma()
        );

        cbReligion.getItems()
                .addAll(
                new TextFileReader()
                        .readFileSingleLine("src/main/resources/textfiles/religion.txt")
                        .getStringArrayDataComma()
        );

        cbSocial.getItems()
                .addAll(
                new TextFileReader()
                        .readFileSingleLine("src/main/resources/textfiles/socialclass.txt")
                        .getStringArrayDataComma()
        );

        toggleEditMode();
    }

    private void hideAccountSettingsButton(){
        btnSettings.setVisible(false);
        btnSettings.setDisable(true);
    }

    private void toggleEditMode() {
        isEditMode = true;

        tfRFID.setEditable(true);
        tfFirstName.setEditable(true);
        tfMiddle.setEditable(true);
        tfLastName.setEditable(true);
        cbQualifier.setEditable(true);
        cbSex.setEditable(true);
        cbCivil.setEditable(true);
        tfVotersID.setEditable(true);
        cbNationality.setEditable(true);
        cbBirthplace.setEditable(true);
        cbPWD.setEditable(true);
        tfOther.setEditable(true);

        tfStreet.setEditable(true);
        tfBarangay.setEditable(true);

        tfEmail.setEditable(true);
        tfMobile.setEditable(true);
        tfLandline.setEditable(true);

        cbReligion.setEditable(true);
        cbEducation.setEditable(true);
        cbOccupation.setEditable(true);
        cbSocial.setEditable(true);
        cbBlood.setEditable(true);
        tfHeight.setEditable(true);
        tfWeight.setEditable(true);

        tfFatherFirst.setEditable(true);
        tfFatherMiddle.setEditable(true);
        tfFatherLast.setEditable(true);
        cbFatherQualifier.setEditable(true);
        cbFatherBirthplace.setEditable(true);
        tfFatherOtherCountry.setEditable(true);

        tfMotherFirst.setEditable(true);
        tfMotherMiddle.setEditable(true);
        tfMotherLast.setEditable(true);
        cbMotherQualifier.setEditable(true);
        cbMotherBirthplace.setEditable(true);
        tfMotherOtherCountry.setEditable(true);

        tfSpouseFirst.setEditable(true);
        tfSpouseMiddle.setEditable(true);
        tfSpouseLast.setEditable(true);
        cbSpouseQualifier.setEditable(true);

        cbQualifier.setDisable(false);
        cbSex.setDisable(false);
        cbCivil.setDisable(false);
        cbNationality.setDisable(false);
        cbBirthplace.setDisable(false);
        cbPWD.setDisable(false);

        cbReligion.setDisable(false);
        cbEducation.setDisable(false);
        cbOccupation.setDisable(false);
        cbSocial.setDisable(false);
        cbBlood.setDisable(false);

        cbFatherQualifier.setDisable(false);
        cbFatherBirthplace.setDisable(false);

        cbMotherQualifier.setDisable(false);
        cbMotherBirthplace.setDisable(false);

        cbSpouseQualifier.setDisable(false);

        btnScan.setDisable(false);
        btnEditPic.setDisable(false);

        btnEditInfo.setText("Save Information");
    }

    private void unToggleEditMode() {
        isEditMode = false;

        tfRFID.setEditable(false);
        tfFirstName.setEditable(false);
        tfMiddle.setEditable(false);
        tfLastName.setEditable(false);
        cbQualifier.setEditable(false);
        cbSex.setEditable(false);
        cbCivil.setEditable(false);
        tfVotersID.setEditable(false);
        cbNationality.setEditable(false);
        cbBirthplace.setEditable(false);
        cbPWD.setEditable(false);
        tfOther.setEditable(false);

        tfStreet.setEditable(false);
        tfBarangay.setEditable(false);

        tfEmail.setEditable(false);
        tfMobile.setEditable(false);
        tfLandline.setEditable(false);

        cbReligion.setEditable(false);
        cbEducation.setEditable(false);
        cbOccupation.setEditable(false);
        cbSocial.setEditable(false);
        cbBlood.setEditable(false);
        tfHeight.setEditable(false);
        tfWeight.setEditable(false);

        tfFatherFirst.setEditable(false);
        tfFatherMiddle.setEditable(false);
        tfFatherLast.setEditable(false);
        cbFatherQualifier.setEditable(false);
        cbFatherBirthplace.setEditable(false);
        tfFatherOtherCountry.setEditable(false);

        tfMotherFirst.setEditable(false);
        tfMotherMiddle.setEditable(false);
        tfMotherLast.setEditable(false);
        cbMotherQualifier.setEditable(false);
        cbMotherBirthplace.setEditable(false);
        tfMotherOtherCountry.setEditable(false);

        tfSpouseFirst.setEditable(false);
        tfSpouseMiddle.setEditable(false);
        tfSpouseLast.setEditable(false);
        cbSpouseQualifier.setEditable(false);

        cbQualifier.setDisable(true);
        cbSex.setDisable(true);
        cbCivil.setDisable(true);
        cbNationality.setDisable(true);
        cbBirthplace.setDisable(true);
        cbPWD.setDisable(true);

        cbReligion.setDisable(true);
        cbEducation.setDisable(true);
        cbOccupation.setDisable(true);
        cbSocial.setDisable(true);
        cbBlood.setDisable(true);

        cbFatherQualifier.setDisable(true);
        cbFatherBirthplace.setDisable(true);

        cbMotherQualifier.setDisable(true);
        cbMotherBirthplace.setDisable(true);

        cbSpouseQualifier.setDisable(true);

        btnEditPic.setDisable(true);
        btnScan.setDisable(true);

        btnEditInfo.setText("Edit Information");
    }

    private Resident buildResident() {
        Resident.ResidentBuilder builder = new Resident.ResidentBuilder(
                tfFirstName.getText().trim(),
                tfMiddle.getText().trim(),
                tfLastName.getText().trim(),
                cbSex.getValue(),
                cbCivil.getValue(),
                cbNationality.getValue(),
                cbBirthplace.getValue(),
                dpBirthdate.getValue()
                        .format(DateTimeFormatter
                                .ofPattern("LLLL dd, yyyy")),
                getPWDvalue(),
                tfStreet.getText(),
                Admin.getInstance().getAdmin().getBarangay(),
                tfFatherFirst.getText().trim(),
                tfFatherMiddle.getText().trim(),
                tfFatherLast.getText().trim(),
                cbFatherBirthplace.getValue(),
                tfMotherFirst.getText().trim(),
                tfMotherMiddle.getText().trim(),
                tfMotherLast.getText().trim(),
                cbMotherBirthplace.getValue(),
                tfRFID.getText()
        );

        if (!tfOther.getText().equals("")){
            builder.setBirthPlace(tfOther.getText());

        } if (!tfMotherOtherCountry.getText().equals("")){
            builder.setMotherBirthPlace(tfMotherOtherCountry.getText());

        } if (!tfFatherOtherCountry.getText().equals("")){
            builder.setFatherBirthPlace(tfFatherOtherCountry.getText());
        }

        builder.setQualifier(cbQualifier.getValue());
        builder.setVotersID(tfVotersID.getText());
        builder.setEmailAddress(tfEmail.getText());
        builder.setMobileNumber(tfMobile.getText());
        builder.setLandline(tfLandline.getText());
        builder.setReligion(cbReligion.getValue());
        builder.setEducationalAttainment(cbEducation.getValue());
        builder.setOccupation(cbOccupation.getValue());
        builder.setSocialClass(cbSocial.getValue());
        builder.setBloodType(cbBlood.getValue());
        builder.setHeight(Integer.parseInt(tfHeight.getText()));
        builder.setWeight(Integer.parseInt(tfWeight.getText()));
        builder.setFatherQualifier(cbFatherQualifier.getValue());
        builder.setMotherQualifier(cbMotherQualifier.getValue());
        builder.setSpouserQualifier(cbSpouseQualifier.getValue());

        return builder.build();
    }

    private boolean getPWDvalue() {
        if (cbPWD.getValue().equals("Yes"))
            return true;
        else if(cbPWD.getValue().equals("No"))
            return false;
        else
            return false;
    }

    private void onCompleteCancelScan() {
        isScanning = false;
        btnScan.setText("Scan");
    }

    private void saveUpdatedInfo(Resident newResident)
            throws FileNotFoundException, JsonProcessingException {

        String adminRFID = Admin
                .getInstance()
                .getAdmin()
                .getUserRFID();

        //if RFID did not update and RFID is of Admin
        if (adminRFID.equals(newResident.getUserRFID())){
            Admin.getInstance().setAdmin(newResident);
            mainController.displayUser();
        }

        //runs if resident updated to a new RFID
        if (!newResident.getUserRFID()
                .equals(resident.getUserRFID())){

            //get Cases first
            List<Case> caseList = DatabaseFacade.getInstance()
                    .getCases(resident.getUserRFID());

            //delete resident record
            Admin.getInstance().deleteResident(resident);

            //add resident record with new RFID entry
            Admin.getInstance().addResident(newResident);

            //add cases
            for (Case aCase : caseList){
                DatabaseFacade.getInstance().addCase(newResident, aCase);
            }

            //if same RFID
        } else {
            Admin.getInstance().editResidentInfo(newResident);
            initData(newResident);
        }

        Admin.getInstance().addLog(LogEvent.UpdateResidentInfo.getEvent());

        new AlertManager(Alert.AlertType.INFORMATION)
                .setMessage("Resident Updated Successfully")
                .show();

        try {

            Official official = DatabaseFacade
                    .getInstance()
                    .getOfficial(
                            resident.getUserRFID(),
                            resident.getBarangay()
                    );
            DatabaseFacade.getInstance()
                    .deleteOfficial(official.getUserRFID());

            official.setFirstName(newResident.getFirstName());
            official.setLastName(newResident.getLastName());
            official.setUserRFID(newResident.getUserRFID());

            DatabaseFacade.getInstance().addOfficial(official);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void saveAddedResident(Resident newResident) throws IOException {
        unToggleEditMode();

        Admin.getInstance()
                .addResident(newResident);

        Admin.getInstance().addLog(LogEvent.ResidentAccountCreation.getEvent());

        //show Resident Added Success
        new AlertManager(Alert.AlertType.INFORMATION)
                .setMessage("Resident Added Successfully")
                .show();

        mainController.switchToResidents(null);
    }

    private boolean isProfileExisting(Resident newResident){
        boolean result = true;

        List<Resident> residentList = DatabaseFacade
                .getInstance()
                .getResidents(
                        Admin.getInstance()
                                .getAdmin()
                                .getBarangay(),
                        ResidentFilterParameter.LastName,
                        newResident.getLastName()
        );

        if (residentList.isEmpty()){
            Logging.printInfoLog("is Empty");
            return false;
        }

        else {
            for (Resident resident : residentList){
                if (!resident.getFirstName().equals(newResident.getFirstName())){
                    Logging.printInfoLog(resident.getFirstName());
                    Logging.printInfoLog(newResident.getFirstName());
                    result = false;
                }

                if (!resident.getBirthDate().equals(newResident.getBirthDate())){
                    Logging.printInfoLog(resident.getBirthDate());
                    Logging.printInfoLog(newResident.getBirthDate());
                    result = false;
                }

                if (!resident.getFatherFirstName().equals(newResident.getFatherFirstName())){
                    Logging.printInfoLog(resident.getFatherFirstName());
                    Logging.printInfoLog(newResident.getFatherFirstName());
                    result = false;
                }

                if (!resident.getFatherLastName().equals(newResident.getFatherLastName())){
                    Logging.printInfoLog(resident.getFatherLastName());
                    Logging.printInfoLog(newResident.getFatherLastName());
                    result = false;
                }

                if (!resident.getFatherMiddleName().equals(newResident.getFatherMiddleName())){
                    Logging.printInfoLog(resident.getFatherMiddleName());
                    Logging.printInfoLog(newResident.getFatherMiddleName());
                    result = false;
                }

                if (!resident.getFatherBirthPlace().equals(newResident.getFatherBirthPlace())){
                    Logging.printInfoLog(resident.getFatherBirthPlace());
                    Logging.printInfoLog(newResident.getFatherBirthPlace());
                    result = false;
                }

                if (!resident.getMotherFirstName().equals(newResident.getMotherFirstName())){
                    Logging.printInfoLog(resident.getMotherFirstName());
                    Logging.printInfoLog(newResident.getMotherFirstName());
                    result = false;
                }

                if (!resident.getMotherLastName().equals(newResident.getMotherLastName())){
                    Logging.printInfoLog(resident.getMotherLastName());
                    Logging.printInfoLog(newResident.getMotherLastName());
                    result = false;
                }

                if (!resident.getMotherMiddleName().equals(newResident.getMotherMiddleName())){
                    Logging.printInfoLog(resident.getMotherMiddleName());
                    Logging.printInfoLog(newResident.getMotherMiddleName());
                    result = false;
                }

                if (!resident.getMotherBirthPlace().equals(newResident.getMotherBirthPlace())){
                    Logging.printInfoLog(resident.getMotherBirthPlace());
                    Logging.printInfoLog(newResident.getMotherBirthPlace());
                    result = false;
                }
            }
        }

        return result;
    }

}
