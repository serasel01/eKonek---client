package com.example.barangayservicesui.utils;

import com.example.BarangayServicesclient.models.Official;
import com.example.BarangayServicesclient.models.Resident;
import com.example.BarangayServicesclient.models.Transaction;
import com.example.barangayservicesui.Main;
import com.example.barangayservicesui.controllers.*;
import com.example.barangayservicesui.enums.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class LoaderUtil{
    public static Stage stage = null;
    private static LoaderUtil single_instance = null;
    private StackPane stackPane;
    private MainController mainController;
    private Resident resident;
    private Official official;

    public static LoaderUtil getLoaderInstance(){
        if (single_instance == null){
            single_instance = new LoaderUtil();
        }
        return single_instance;
    }

    public static Stage getStage() {
        return stage;
    }

    public MainController getMainController() {
        return mainController;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public Official getOfficial() {
        return official;
    }

    public LoaderUtil setOfficial(Official official) {
        this.official = official;
        return this;
    }

    public FXMLLoader loadScene(String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlName));
        initScene(loader).show();
        return loader;
    }

    private Stage initScene(FXMLLoader loader) throws IOException {
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Barangay Services");
        stage.centerOnScreen(); // to make stage in the center
        return stage;
    }

    public void showLogin() throws IOException {
        LoginController loginController =  LoaderUtil.getLoaderInstance()
                .loadScene("views/login-view.fxml")
                .getController();
        loginController.start();
    }

    public void showMain() throws IOException {
        stackPane = new StackPane();
        HashMap<Integer, Object> controllers = new HashMap<>();

        mainController = LoaderUtil.getLoaderInstance()
                .loadScene("views/main-dashboard-view.fxml")
                .getController();
        mainController.start(stackPane);
    }

    public void deleteNode() {
        stackPane.getChildren().clear();
    }

    public void loadNode(Pane pane) throws IOException {
        FXMLLoader loader;

        switch (pane){
            case NewProfile -> {
                loader = new FXMLLoader(Main.class.getResource(Pane.NewProfile.getFilePath()));
                stackPane.getChildren().add(loader.load());

                ProfileController profileController = loader.getController();
                profileController.start(mainController);
            }

            case Profile -> {
                loader = new FXMLLoader(Main.class.getResource(Pane.Profile.getFilePath()));
                stackPane.getChildren().add(loader.load());

                ProfileController profileController = loader.getController();
                profileController.start(mainController);
                profileController.initData(resident);
            }

            case Officials -> {
                loader = new FXMLLoader(Main.class.getResource(Pane.Officials.getFilePath()));
                stackPane.getChildren().add(loader.load());

                ManageOfficialController manageOfficialController = loader.getController();
                manageOfficialController.start(mainController);
            }

            case Transactions -> {
                loader = new FXMLLoader(Main.class.getResource(Pane.Transactions.getFilePath()));
                stackPane.getChildren().add(loader.load());

                ManageTransactionsController manageTransactionsController = loader.getController();
                manageTransactionsController.start();
            }

            case Residents -> {
                loader = new FXMLLoader(Main.class.getResource(Pane.Residents.getFilePath()));
                stackPane.getChildren().add(loader.load());

                ManageResidentController manageResidentController = loader.getController();
                manageResidentController.start(mainController);
            }

            case SystemLogs -> {
                loader = new FXMLLoader(Main.class.getResource(Pane.SystemLogs.getFilePath()));
                stackPane.getChildren().add(loader.load());

                LogController logController = loader.getController();
                logController.start();
            }
        }
    }

    public void loadCamera(String userRFID, ProfileController profileController) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/camera-view.fxml"));

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.getDialogPane().getChildren().add(loader.load());
        dialog.setResizable(true);
        dialog.getDialogPane().setMinSize(900, 690);

        CameraController cameraController = loader.getController();
        cameraController.setData(userRFID, dialog, profileController);

        dialog.show();
    }

    public void loadCases(Resident resident) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/cases-view.fxml"));

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.getDialogPane().getChildren().add(loader.load());
        dialog.setResizable(true);
        dialog.getDialogPane().setMinSize(600, 800);

        CaseController caseController = loader.getController();
        caseController.setData(resident, dialog);

        dialog.show();
    }

    public void loadTransaction(Transaction transaction) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/transaction-view.fxml"));

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.getDialogPane().getChildren().add(loader.load());
        dialog.setResizable(true);
        dialog.getDialogPane().setMinSize(425, 405);

        TransactionController transactionController =
                loader.getController();
        transactionController.start(dialog);

        if (transaction != null){
            transactionController.setData(transaction);
        }

        dialog.show();
    }

    public void loadOfficial() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/official-view.fxml"));

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.getDialogPane().getChildren().add(loader.load());
        dialog.setResizable(true);
        dialog.getDialogPane().setMinSize(414, 345);

        OfficialController officialController =
                loader.getController();
        officialController.start(dialog);

        if (this.official != null){
            officialController.setData(this.official);
        }

        dialog.show();
    }

    public void loadSettings() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/account-settings-view.fxml"));

        Dialog<Boolean> dialog = new Dialog<>();
        dialog.getDialogPane().getChildren().add(loader.load());
        dialog.setResizable(true);
        dialog.getDialogPane().setMinSize(425, 380);

        AccountSettingsController accountSettingsController =
                loader.getController();
        accountSettingsController
                .setData(
                        Admin.getInstance().getAdmin().getUserRFID(),
                        dialog);

        dialog.show();
    }

}
