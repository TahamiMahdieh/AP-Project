package org.Database;

import org.common.User;

import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;

public class DataBaseActions {
    private final Connection connection;

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
    public boolean setFirstname(String email, String newFirstname) {
        return setStringToUsers(email, "firstname", newFirstname);
    }
    public String getLastname(String email) {
        return getStringFromUsers(email, "lastname");
    }

    public boolean setLastname(String email, String newLastname) {
        return setStringToUsers(email, "lastname", newLastname);
    }

    public String getAdditionalName(String email) {
        return getStringFromUserProfile(email, "additional_name");
    }

    public boolean setAdditionalName(String email, String newAdditionalName) {
        return setStringToUserProfile(email, "additional_name", newAdditionalName);
    }

    public String getProfilePicture(String email) {
        return getPicture(email, "profile_picture");
    }

    public boolean setProfilePicture(String email, Path filePath) {
        return setFilePathToUserProfile(email, "profile_picture", filePath);
    }

    public String getBackgroundPicture(String email) {
        return getPicture(email, "background_picture");
    }

    public boolean setBackgroundPicture(String email, Path filePath) {
        return setFilePathToUserProfile(email, "background_picture", filePath);
    }

    public String getHeadline(String email) {
        return getStringFromUserProfile(email, "headline");
    }

    public boolean setHeadline(String email, String newHeadline) {
        return setStringToUserProfile(email, "headline", newHeadline);
    }

    public String getProfileUrl(String email) {
        return getStringFromContactInfo(email, "profile_url");
    }

    // profile url cannot be set, it is generated.

    // contact info email must not be mistaken with the email used for sign up.
    public String getContactInfoEmail(String email) {
        return getStringFromContactInfo(email, "email");
    }

    public boolean setContactInfoEmail(String email, String contactInfoEmail) {
        return true;
    }

    public String getPhoneNumber(String email) {
        return getStringFromContactInfo(email, "phone_number");
    }

    public boolean setPhoneNumber(String email, String newPhoneNumber) {
        return true;
    }

    public String getPhoneType(String email) {
        return getStringFromContactInfo(email, "phone_type");
    }

    public boolean setPhoneType(String email, String newPhoneType) {
        return true;
    }

    public String getAddress(String email) {
        return getStringFromContactInfo(email, "address");
    }

    public boolean setAddress(String email, String newAddress) {
        return true;
    }

    public Date getBirthDate(String email) {
        return getDateFromContactInfo(email, "birth_date");
    }

    public boolean setBirthDate(String email, Date newBirthDate) {
        return true;
    }

    public String getBirthDatePrivacy(String email) {
        return getStringFromContactInfo(email, "birth_date_privacy");
    }

    public boolean setBirthDatePrivacy(String email, String newBirthDatePrivacy) {
        return true;
    }

    public String getInstantMessaging(String email) {
        return getStringFromContactInfo(email, "instant_messaging");
    }

    public boolean setInstantMessaging(String email, String newInstantMessaging) {
        return true;
    }

    private String getStringFromUsers(String email, String label) {
        String query = "SELECT " + label + " FROM users WHERE email = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getNString(label);
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
                return resultSet.getInt(label);
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
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getNString(label);
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
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(label);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String getPicture(String email, String label) {
        int id = getIntFromUsers(email, "user_profile_id");
        String query = "SELECT " + label + " FROM user_profile WHERE id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(label);
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
            statement.setString(1, String.valueOf(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getNString(label);
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
                return resultSet.getInt(label);
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
                return resultSet.getDate(label);
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
                return resultSet.getBoolean(label);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }





    private boolean setStringToUsers(String email, String label, String newStr) {
        String query = "UPDATE users SET " + label + " = \"" + newStr + "\" WHERE email = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.executeUpdate();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean setStringToUserProfile(String email, String label, String newStr) {
        int id = getIntFromUsers(email, "user_profile_id");
        String query = "UPDATE user_profile SET " + label + " = \"" + newStr + "\" WHERE id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(id));
            statement.executeUpdate();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean setFilePathToUserProfile(String email, String label, Path filePath) {
        int id = getIntFromUsers(email, "user_profile_id");
        String newPath = filePath.toString().replace("\\", "/"); // for some reason the former doesn't work
        String query = "UPDATE user_profile SET " + label + " = \"" + newPath + "\" WHERE id = ?;";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(id));
            statement.executeUpdate();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }










    public boolean addUserToUsersTable (User user) {
        try {
            connection.setAutoCommit(false);

            String addContactInfo = "INSERT INTO contact_info () VALUES ();";
            int contact_info_id = insertAndGetId(connection, addContactInfo);

            String addUserProfile = "INSERT INTO user_profile (contact_info_id) VALUES (?);";
            int user_profile_id = insertAndGetId(connection, addUserProfile, contact_info_id);

            String insertIntoUsers = "INSERT INTO users (firstname, lastname, email, pass, user_profile_id) VALUES (?, ? , ? , ?, ?);";
            int user_id = insertAndGetId(connection, insertIntoUsers, user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword(), user_profile_id);

            connection.commit();

            return true;

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Transaction rolled back due to error: " + e.getMessage());
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
            return false;

        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*public boolean addEducation(String email, String schoolName, String fieldOfStudy, Date startDate, Date endDate, double grade, String activitiesAndSocieties, String description, JsonArray skills, boolean notifyNetwork) {
        try {
            connection.setAutoCommit(false);
            String getUserId = "SELECT id FROM users WHERE email = ?;";
            PreparedStatement stmt = connection.prepareStatement(getUserId);
            ;
            int userId;
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                     userId = rs.getInt(1);
                }
            }
            String addEducation = "INSERT INTO education () VALUES ();";

        } catch (SQLException e) {

        }
    }*/

    private static int insertAndGetId(Connection conn, String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Insertion failed, no ID obtained.");
                }
            }
        }
    }
    public ArrayList<String> search (String phrase) {
        ArrayList<String> foundEmails = new ArrayList<>();
        //String query = "SELECT email FROM users WHERE firstname LIKE ? OR lastname LIKE ? OR email LIKE ?";
        String query = "SELECT users.*, user_profile.* FROM users JOIN user_profile ON users.user_profile_id = user_profile.id WHERE "
                + "users.firstname LIKE ? "
                + "Or users.lastname LIKE ? "
                + "OR users.email LIKE ? "
                + "OR user_profile.headline LIKE ? "
                + "OR user_profile.additional_name LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the parameters for the prepared statement
            String searchPhrase = "%" + phrase + "%";
            statement.setString(1, searchPhrase);
            statement.setString(2, searchPhrase);
            statement.setString(3, searchPhrase);
            statement.setString(4,searchPhrase);
            statement.setString(5, searchPhrase);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foundEmails.add(getFirstname(resultSet.getString("email")) + " " + getLastname(resultSet.getString("email")) + " -> " + resultSet.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundEmails;
    }

    private static void insertWithTwoIds(Connection conn, String sql, long id1, long id2) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id1);
            stmt.setLong(2, id2);
            stmt.executeUpdate();
        }
    }

}
