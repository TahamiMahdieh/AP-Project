package org.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
//    private static final String URL = "jdbc:mysql://localhost:5000/Project";
    private static final String URL = "jdbc:mysql://localhost:3306/Project";
    private static final String USER = "root";
//    private static final String PASSWORD = "SQL-Account/Mah83?@Ta";
    private static final String PASSWORD = "Dragonfly/345";
    private static Connection connection = null;

    public static Connection getConnection (){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
