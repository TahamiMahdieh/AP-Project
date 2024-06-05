package org.Database;

import com.mysql.cj.xdevapi.JsonArray;
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

    public boolean setFirstname(String email, String newFirstname) {
        return true;
    }

    public String getLastname(String email) {
        return getStringFromUsers(email, "lastname");
    }

    public boolean setLastname(String email, String newLastname) {
        return true;
    }

    public String getAdditionalName(String email) {
        return getStringFromUserProfile(email, "additional_name");
    }

    public boolean setAdditionalName(String email, String newAdditionalName) {
        return true;
    }

    public Blob getProfilePicture(String email) {
        return getBlobFromUserProfile(email, "profile_picture");
    }

    public boolean setProfilePicture(String email, String filePath) {
        return true;
    }

    public Blob getBackgroundPicture(String email) {
        return getBlobFromUserProfile(email, "background_picture");
    }

    public boolean setBackgroundPicture(String email, String filePath) {
        return true;
    }

    public String getHeadline(String email) {
        return getStringFromUserProfile(email, "headline");
    }

    public boolean setHeadline(String email, String newHeadline) {
        return true;
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

    private static void insertWithTwoIds(Connection conn, String sql, long id1, long id2) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id1);
            stmt.setLong(2, id2);
            stmt.executeUpdate();
        }
    }

}
