package com.example.barangayservicesui.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertManager {
    private Alert alert;

    public AlertManager(Alert.AlertType alertType) {
        this.alert = new Alert(alertType);
        this.alert.setTitle(alertType.name());
    }

    public AlertManager setMessage(String message){
        this.alert.setHeaderText(message);
        return this;
    }

    public boolean showAndWait(){
        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            return true;
        } else if (option.isPresent() && option.get() == ButtonType.CANCEL) {
            return false;
        } else {
            return false;
        }
    }

    public void show(){
        this.alert.show();
    }

}
