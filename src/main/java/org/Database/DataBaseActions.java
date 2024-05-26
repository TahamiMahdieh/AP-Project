package org.Database;

import org.common.User;

import java.sql.*;

public class DataBaseActions {
    private Connection connection;

    public DataBaseActions() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean doesEmailExist (String email){
        String query = "SELECT COUNT(*) FROM users where email = ? ;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);// this converts our query to sql code
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean addUserToUsersTable (User user){
        String query = "INSERT INTO users (firstname, lastname, email, pass) VALUES (?, ? , ? , ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getFirstname());
            statement.setString(2, user.getLastname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());

            statement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            return false; // adding user to database failed
        }
    }
}
