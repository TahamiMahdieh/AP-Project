package org.Database;

import org.common.SafeRunning;

import java.sql.Connection;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

public abstract class Table {
    public void createTable(){};
    public void createTable(String username){};
    protected Connection getConnection() { // Connection is used to connect the database
        return SQLConnection.getInstance().getConnection();
    }

    protected boolean executeUpdate(String query) {
        return SafeRunning.safe(() -> {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(query);
            statement.close();
        });
    }

}
