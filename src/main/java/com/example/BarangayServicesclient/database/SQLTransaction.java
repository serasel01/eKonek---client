package com.example.BarangayServicesclient.database;

import com.example.BarangayServicesclient.enums.TransactionFilter;
import com.example.BarangayServicesclient.models.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class SQLTransaction extends SQLConnect {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Override
    public int getTotalCount() {
        int totalTransactions = 0;

        try {
            this.connect();

            preparedStatement = this.getConnection()
                    .prepareStatement(
                            "SELECT COUNT(*) AS total " +
                                    "FROM dbekonek.tbtransaction " +
                                    "WHERE transactionType = ?"
                    );
            preparedStatement.setString(1, this.getEntry());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                totalTransactions = resultSet.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (preparedStatement != null){
                    preparedStatement.close();
                }
                if (resultSet != null){
                    resultSet.close();
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        this.disconnect();
        return totalTransactions;
    }

    @Override
    public List<?> getList() {
        List<Transaction> transactionList = new ArrayList<>();

        try {
            this.connect();

            preparedStatement = this.getConnection()
                    .prepareStatement(getTransactionFilter().getQuery());
            preparedStatement.setString(1, this.getEntry());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                transactionList.add(
                        new Transaction(
                                resultSet.getString("transactionID"),
                                resultSet.getString("transactionDate"),
                                resultSet.getString("transactionType"),
                                resultSet.getString("residentName"),
                                resultSet.getString("officialName"),
                                resultSet.getString("transactionStatus"),
                                resultSet.getString("residentRFID")
                        ));
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (preparedStatement != null){
                    preparedStatement.close();
                }
                if (resultSet != null){
                    resultSet.close();
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        this.disconnect();
        return transactionList;
    }

    @Override
    public void add() {
        this.connect();

        try {
            preparedStatement = this.getConnection()
                    .prepareStatement(
                            "INSERT INTO dbekonek.tbtransaction " +
                            "(transactionID, transactionDate, transactionType, " +
                            "residentName, officialName, transactionStatus, " +
                            "residentRFID) values (?,?,?,?,?,?,?)"
                    );

            preparedStatement.setString(
                    1,
                    this.getTransaction().getTransactionID()
            );

            preparedStatement.setString(
                    2,
                    this.getTransaction().getTransactionDate()
            );

            preparedStatement.setString(
                    3,
                    this.getTransaction().getTransactionType()
            );

            preparedStatement.setString(
                    4,
                    this.getTransaction().getResidentName()
            );

            preparedStatement.setString(
                    5,
                    this.getTransaction().getOfficialName()
            );

            preparedStatement.setString(
                    6,
                    this.getTransaction().getTransactionStatus()
            );

            preparedStatement.setString(
                    7,
                    this.getTransaction().getResidentRFID()
            );

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (preparedStatement != null){
                    preparedStatement.close();
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        this.disconnect();
    }

    @Override
    public void update() {
        this.connect();

        try {
            preparedStatement = this.getConnection()
                    .prepareStatement(
                            "UPDATE dbekonek.tbtransaction " +
                                    "SET transactionStatus = ? " +
                                    "WHERE transactionID = ?"
                    );

            preparedStatement.setString(
                    1,
                    this.getTransaction().getTransactionStatus()
            );

            preparedStatement.setString(
                    2,
                    this.getTransaction().getTransactionID()
            );

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                if (preparedStatement != null){
                    preparedStatement.close();
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        this.disconnect();
    }
}
