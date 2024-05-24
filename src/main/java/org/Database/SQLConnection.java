package org.Database;

import org.common.SafeRunning;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    private static SQLConnection instance = null;
    Connection connection = null;

    public static SQLConnection getInstance() {
        if (instance == null) {
            instance = new SQLConnection();
        }
        return instance;
    }
    public synchronized static void closeConnection() {
        if (instance != null) {
            instance.close();
        }
    }

    public void connect() throws SQLException{
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:5000/Project", "root", "SQL-Account/Mah83?@Ta");
        }
        createTables();
    }

    public void close() {
        if (connection != null) {
            SafeRunning.safe(connection::close);
            instance = null;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    // TABLES
    final UserTable users = new UserTable();

    private void createTables() {
        users.createTable();
        //createFollowTable();
        //createBlockTable();
    }

    public static UserTable getUsers() {
        return getInstance().users;
    }
    /*public static final FollowTable followTable = new FollowTable();
    public FollowTable createFollowTable() {
        followTable.createTable();
        return followTable;
    }

    public static FollowTable getFollowTable() {
        return followTable;
    }

    public static final BlockTable blockTable = new BlockTable();
    public BlockTable createBlockTable() {
        blockTable.createTable();
        return blockTable;
    }

    public static BlockTable getBlockTable() {
        return blockTable;
    }*/
}
