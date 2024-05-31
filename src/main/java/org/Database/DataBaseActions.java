package org.Database;

import org.common.Response;
import org.common.User;

import java.sql.*;

public class DataBaseActions {
    private Connection connection;

    public DataBaseActions() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean doesEmailExist (String email){
        String query = "SELECT COUNT(*) FROM users WHERE email = ?;";
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
    public boolean checkPassword(String email, String givenPassword) {
        String query = "SELECT pass FROM users WHERE email = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String correctPassword = resultSet.getNString("pass");
                return givenPassword.equals(correctPassword);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getFirstname(String email) {
        return getStringFromUsers(email, "firstname");
    }

    public String getLastname(String email) {
        return getStringFromUsers(email, "lastname");
    }

    public String getAdditionalName(String email) {
        return getStringFromUserProfile(email, "additional_name");
    }

    public Blob getProfilePicture(String email) {
        return getBlobFromUserProfile(email, "profile_picture");
    }

    public Blob getBackgroundPicture(String email) {
        return getBlobFromUserProfile(email, "background_picture");
    }

    public String getHeadline(String email) {
        return getStringFromUserProfile(email, "headline");
    }

    public String getProfileUrl(String email) {
        return getStringFromContactInfo(email, "profile_url");
    }

    public String getContactInfoEmail(String email) {
        return getStringFromContactInfo(email, "email");
    }

    public String getPhoneNumber(String email) {
        return getStringFromContactInfo(email, "phone_number");
    }

    public String getPhoneType(String email) {
        return getStringFromContactInfo(email, "phone_type");
    }

    public String getAddress(String email) {
        return getStringFromContactInfo(email, "address");
    }

    public Date getBirthDate(String email) {
        return getDateFromContactInfo(email, "birth_date");

    }

    public String getBirthDatePrivacy(String email) {
        return getStringFromContactInfo(email, "birth_date_privacy");
    }

    public String getInstantMessaging(String email) {
        return getStringFromContactInfo(email, "instant_messaging");
    }

    private String getStringFromUsers(String email, String label) {
        String query = "SELECT " + label + " FROM users WHERE email = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String answer = resultSet.getNString(label);
                return answer;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getIntFromUsers(String email, String label) {
        String query = "SELECT " + label + " FROM users WHERE email = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int answer = resultSet.getInt(label);
                return answer;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String getStringFromUserProfile(String email, String label) {
        int id = getIntFromUsers(email, "user_profile_id");
        String query = "SELECT " + label + " FROM user_profile WHERE id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String answer = resultSet.getNString(label);
                return answer;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getIntFromUserProfile(String email, String label) {
        int id = getIntFromUsers(email, "user_profile_id");
        String query = "SELECT " + label + " FROM user_profile WHERE id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int answer = resultSet.getInt(label);
                return answer;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Blob getBlobFromUserProfile(String email, String label) {
        int id = getIntFromUsers(email, "user_profile_id");
        String query = "SELECT " + label + " FROM user_profile WHERE id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Blob answer = resultSet.getBlob(label);
                return answer;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getStringFromContactInfo(String email, String label) {
        int userProfileId = getIntFromUsers(email, "user_profile_id");
        int id = getIntFromUserProfile(email, "contact_info_id");
        String query = "SELECT " + label + " FROM contact_info WHERE id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String answer = resultSet.getNString(label);
                return answer;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getIntFromContactInfo(String email, String label) {
        int userProfileId = getIntFromUsers(email, "user_profile_id");
        int id = getIntFromUserProfile(email, "contact_info_id");
        String query = "SELECT " + label + " FROM contact_info WHERE id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int answer = resultSet.getInt(label);
                return answer;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Date getDateFromContactInfo(String email, String label) {
        int userProfileId = getIntFromUsers(email, "user_profile_id");
        int id = getIntFromUserProfile(email, "contact_info_id");
        String query = "SELECT " + label + " FROM contact_info WHERE id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Date answer = resultSet.getDate(label);
                return answer;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean getBooleanFromContactInfo(String email, String label) {
        int userProfileId = getIntFromUsers(email, "user_profile_id");
        int id = getIntFromUserProfile(email, "contact_info_id");
        String query = "SELECT " + label + " FROM contact_info WHERE id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                boolean answer = resultSet.getBoolean(label);
                return answer;
            }
        }
        catch (Exception e) {
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
