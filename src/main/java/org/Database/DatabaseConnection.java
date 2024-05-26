package org.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.Class.forName;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:5000/Project";
    //private static final String URL = "jdbc:mysql://localhost:3306/Project";
    private static final String USER = "root";

    private static final String PASSWORD = "SQL-Account/Mah83?@Ta";
    //private static final String PASSWORD = "Dragonfly/345";
    private static Connection connection = null;

    public static Connection getConnection (){
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
