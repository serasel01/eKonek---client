module BarangayServices.client {
    requires spring.web;
    requires java.logging;
    requires spring.webflux;
    requires reactor.core;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires static lombok;

    opens com.example.BarangayServicesclient to lombok;
    opens com.example.BarangayServicesclient.models to lombok;
    exports com.example.BarangayServicesclient;
    exports com.example.BarangayServicesclient.models;
}