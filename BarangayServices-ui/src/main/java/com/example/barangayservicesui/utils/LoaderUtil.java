package com.example.barangayservicesui.utils;

import com.example.barangayservicesui.Main;
import com.example.barangayservicesui.controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class LoaderUtil{
    public static Stage stage = null;
    private static LoaderUtil single_instance = null;
    private StackPane stackPane;

    public static LoaderUtil getLoaderInstance(){
        if (single_instance == null){
            single_instance = new LoaderUtil();
        }
        return single_instance;
    }

    public FXMLLoader loadStage(String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlName));
        initStage(loader).show();
        return loader;
    }

    private Stage initStage(FXMLLoader loader) throws IOException {
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Barangay Services");
        stage.centerOnScreen(); // to make stage in the center
        return stage;
    }

    public void showLogin() throws IOException {
        LoginController loginController =  LoaderUtil.getLoaderInstance()
                .loadStage("views/login-view.fxml")
                .getController();
        loginController.start();
    }

    public void showMain() throws IOException {
        stackPane = new StackPane();
        HashMap<Integer, Object> controllers = new HashMap<>();

        MainController mainController = LoaderUtil.getLoaderInstance()
                .loadStage("views/main-view.fxml")
                .getController();
        mainController.start(stackPane, controllers);

        initNodes(mainController, controllers);
    }

    private void initNodes(MainController mainController, HashMap<Integer, Object> controllers) throws IOException {
        FXMLLoader loader;

        for (int i = 3; i>=1; i--){
            switch (i) {
                case 3 -> {
                    loader = new FXMLLoader(Main.class.getResource("views/admin-view.fxml"));
                    stackPane.getChildren().add(loader.load());

                    AdminController adminController = loader.getController();
                    adminController.start();
                    controllers.put(i, adminController);
                }
                case 2 -> {
                    loader = new FXMLLoader(Main.class.getResource("views/profile-view.fxml"));
                    stackPane.getChildren().add(loader.load());

                    ProfileController profileController = loader.getController();
                    profileController.start(mainController);
                    controllers.put(i, profileController);
                }
                case 1 -> {
                    loader = new FXMLLoader(Main.class.getResource("views/manage-view.fxml"));
                    stackPane.getChildren().add(loader.load());

                    ManageController manageController = loader.getController();
                    manageController.start(mainController);
                    controllers.put(i, manageController);
                }
            }
        }
    }
}
