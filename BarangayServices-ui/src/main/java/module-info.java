module com.example.barangayservicesui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires BarangayServices.client;
    requires org.apache.commons.codec;
    requires java.prefs;
    requires java.sql;
    requires webcam.capture;
    requires java.desktop;
    requires javafx.swing;
    requires spire.doc.free;
    requires com.fasterxml.jackson.core;

    opens com.example.barangayservicesui to javafx.fxml;
    exports com.example.barangayservicesui;
    exports com.example.barangayservicesui.controllers;
    exports com.example.barangayservicesui.utils;
    opens com.example.barangayservicesui.controllers to javafx.fxml;

}