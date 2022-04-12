package com.example.barangayservicesui;

import com.example.barangayservicesui.utils.LoaderUtil;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        
        LoaderUtil.stage = stage;
        LoaderUtil.getLoaderInstance().showLogin();
    }

    public static void main(String[] args) throws IOException {
        runBackendService();
        launch(args);
    }

    //runs the backend service
    private static void runBackendService() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "src/main/resources/BarangayServices-0.0.1-SNAPSHOT.jar");
        pb.start();
    }
}