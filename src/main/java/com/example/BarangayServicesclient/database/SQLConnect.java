package com.example.BarangayServicesclient.database;

import com.example.BarangayServicesclient.enums.SystemEventFilter;
import com.example.BarangayServicesclient.enums.TransactionFilter;
import com.example.BarangayServicesclient.models.SystemEvent;
import com.example.BarangayServicesclient.models.Transaction;

import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SQLConnect {
    final private static Logger logger = Logger.getLogger(SQLConnect.class.getName());

    private Connection connection = null;

    private String entry;
    private TransactionFilter transactionFilter;
    private SystemEventFilter systemEventFilter;

    private Transaction transaction;
    private SystemEvent systemEvent;

    //establishes the connection
    protected void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/dbekonek",
                    "root",
                    "");
            logger.info("DB STATUS: CONNECTED");

        } catch (Exception e){
            logger.log(Level.SEVERE, "Not Connected: ", e);
        }
    }

    //disconnects the db connection after doing any CRUD operation
    protected void disconnect(){
        try{
            if (connection!= null){
                this.connection.close();
                logger.info("DB STATUS: DISCONNECTED");
            }
        } catch (Exception e){
            logger.log(Level.SEVERE, "Not Connected: ", e);
        }
    }

    public  Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public TransactionFilter getTransactionFilter() {
        return transactionFilter;
    }

    public SQLConnect setTransactionFilter(TransactionFilter transactionFilter) {
        this.transactionFilter = transactionFilter;
        return this;
    }

    public String getEntry() {
        return entry;
    }

    public SQLConnect setEntry(String entry) {
        this.entry = entry;
        return this;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public SQLConnect setTransaction(Transaction transaction) {
        this.transaction = transaction;
        return this;
    }

    public SystemEvent getSystemEvent() {
        return systemEvent;
    }

    public SQLConnect setSystemEvent(SystemEvent systemEvent) {
        this.systemEvent = systemEvent;
        return this;
    }

    public SystemEventFilter getSystemEventFilter() {
        return systemEventFilter;
    }

    public SQLConnect setSystemEventFilter(SystemEventFilter systemEventFilter) {
        this.systemEventFilter = systemEventFilter;
        return this;
    }

    public int getTotalCount(){
        return 0;
    }

    public abstract List<?> getList() throws SQLException;
    public abstract void add();
    public abstract void update();

}
