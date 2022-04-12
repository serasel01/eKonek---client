module BarangayServices.client {
    requires spring.web;
    requires java.logging;
    requires spring.webflux;
    requires reactor.core;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    exports com.example.BarangayServicesclient;
    exports com.example.BarangayServicesclient.models;
    exports com.example.BarangayServicesclient.enums;
}