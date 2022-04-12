package com.example.BarangayServicesclient.database;

import com.example.BarangayServicesclient.models.SystemEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLSystemEvent extends SQLConnect{
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Override
    public List<?> getList() {
        List<SystemEvent> systemEventList = new ArrayList<>();

        try {
            this.connect();

            preparedStatement = this.getConnection()
                    .prepareStatement(getSystemEventFilter().getQuery());
            preparedStatement.setString(1, this.getEntry());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                systemEventList.add(
                        new SystemEvent(
                                resultSet.getString("event"),
                                resultSet.getString("timestamp"),
                                resultSet.getString("officialName"),
                                resultSet.getString("officialID")
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
        return systemEventList;
    }

    @Override
    public void add() {
        this.connect();

        try {
            preparedStatement = this.getConnection()
                    .prepareStatement(
                            "INSERT INTO dbekonek.tbsystemevents " +
                                    "(event, timestamp, officialName, " +
                                    "officialID) values (?,?,?,?)"
                    );

            preparedStatement.setString(
                    1,
                    this.getSystemEvent().getEvent()
            );

            preparedStatement.setString(
                    2,
                    this.getSystemEvent().getTimestamp()
            );

            preparedStatement.setString(
                    3,
                    this.getSystemEvent().getOfficialName()
            );

            preparedStatement.setString(
                    4,
                    this.getSystemEvent().getOfficialID()
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
    }
}
