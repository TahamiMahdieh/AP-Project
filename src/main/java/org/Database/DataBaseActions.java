package org.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseActions {
    private Connection connection;

    public DataBaseActions() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean doesEmailExist (){
        String query = "SELECT COUNT(email) FROM users;";
        try {
            PreparedStatement statement = connection.prepareStatement(query); // this converts our query to sql code
            int result = statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
