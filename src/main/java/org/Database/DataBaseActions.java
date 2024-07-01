package org.Database;

import org.common.GetConnectionReturn;
import org.common.PostObject;
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
    public void followUsingEmail (String followerEmail, String followeeEmail){
        int followerId = getIntFromUsers(followerEmail, "id");
        int followeeId = getIntFromUsers(followeeEmail, "id");
        String query = "INSERT INTO follows (follower_id, followee_id) VALUES (?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, String.valueOf(followerId));
            preparedStatement.setString(2,String.valueOf(followeeId));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void followUsingId (int followerId, int followeeId){
        String query = "INSERT INTO follows (follower_id, followee_id) VALUES (?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(2,String.valueOf(followeeId));
            preparedStatement.setString(1, String.valueOf(followerId));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void unfollowUsingEmail (String followerEmail, String followeeEmail){
        int followerId = getIntFromUsers(followerEmail, "id");
        int followeeId = getIntFromUsers(followeeEmail, "id");
        String query = "DELETE FROM Follows WHERE follower_id = ? AND followee_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, String.valueOf(followerId));
            preparedStatement.setString(2,String.valueOf(followeeId));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getFollowersEmailUsingEmail (String followeeEmail){
        ArrayList<String> followersEmail = new ArrayList<>();
        int followeeId = getIntFromUsers(followeeEmail, "id");
        String query = "SELECT email FROM users JOIN follows ON users.id = follows.follower_id WHERE follows.followee_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, String.valueOf(followeeId));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    followersEmail.add(resultSet.getString("email"));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return followersEmail;
    }
    public ArrayList<String> getFollowersEmailUsingId (int followeeId){
        ArrayList<String> followersEmail = new ArrayList<>();
        String query = "SELECT email FROM users JOIN follows ON users.id = follows.follower_id WHERE follows.followee_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, String.valueOf(followeeId));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    followersEmail.add(resultSet.getString("email"));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return followersEmail;
    }
    public ArrayList<String> getFollowersInfoUsingEmail (String followeeEmail){
        ArrayList<String> followersEmail = new ArrayList<>();
        int followeeId = getIntFromUsers(followeeEmail, "id");
        String query = "SELECT * FROM users JOIN follows ON users.id = follows.follower_id WHERE follows.followee_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, String.valueOf(followeeId));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    followersEmail.add(resultSet.getString("firstname") + " " + resultSet.getString("lastname") + " -> " + resultSet.getString("email"));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return followersEmail;
    }
    public ArrayList<String> getFolloweeEmailsUsingEmail (String followerEmail){
        ArrayList<String> followeeEmails = new ArrayList<>();
        int followerId = getIntFromUsers(followerEmail, "id");
        String query = "SELECT email FROM users JOIN follows ON users.id = follows.followee_id WHERE follows.follower_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, String.valueOf(followerId));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    followeeEmails.add(resultSet.getString("email"));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return followeeEmails;
    }
    public ArrayList<String> getFolloweeEmailUsingId (int followerId){
        ArrayList<String> followeeEmails = new ArrayList<>();
        String query = "SELECT email FROM users JOIN follows ON users.id = follows.followee_id WHERE follows.follower_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, String.valueOf(followerId));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    followeeEmails.add(resultSet.getString("email"));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return followeeEmails;
    }
    public ArrayList<String> getFolloweeInfoUsingEmail(String followerEmail){
        ArrayList<String> followeeEmails = new ArrayList<>();
        int followerId = getIntFromUsers(followerEmail, "id");
        String query = "SELECT * FROM users JOIN follows ON users.id = follows.followee_id WHERE follows.follower_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, String.valueOf(followerId));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                while (resultSet.next()) {
                    followeeEmails.add(resultSet.getString("firstname") + " " + resultSet.getString("lastname") + " -> " + resultSet.getString("email"));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return followeeEmails;
    }
    public void sendConnectionRequest (String senderEmail, String receiverEmail, String note){
        int senderId = getIntFromUsers(senderEmail, "id");
        int receiverId = getIntFromUsers(receiverEmail, "id");
        String query = "INSERT INTO connectionRequests (sender_id, receiver_id, status, note) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, String.valueOf(senderId));
            statement.setString(2, String.valueOf(receiverId));
            statement.setString(3, "pending");
            statement.setString(4, note);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void acceptConnection (String senderEmail, String receiverEmail){
        int senderId = getIntFromUsers(senderEmail, "id");
        int receiverId = getIntFromUsers(receiverEmail, "id");
        String query1 = "INSERT INTO connections (user1_id, user2_id) VALUES (?, ?);";
        String query2 = "UPDATE connectionRequests SET status = 3 WHERE sender_id = ? AND receiver_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query1);
             PreparedStatement statement2 = connection.prepareStatement(query2)){
            statement.setString(1, String.valueOf(senderId));
            statement.setString(2, String.valueOf(receiverId));
            statement.executeUpdate();

            statement2.setString(1, String.valueOf(senderId));
            statement2.setString(2, String.valueOf(receiverId));
            statement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void rejectConnectionRequest (String senderEmail, String receiverEmail){
        int senderId = getIntFromUsers(senderEmail, "id");
        int receiverId = getIntFromUsers(receiverEmail, "id");
        String query = "UPDATE connectionRequests SET status = 2 WHERE sender_id = ? AND receiver_id = ? ;";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, String.valueOf(senderId));
            statement.setString(2, String.valueOf(receiverId));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void disconnect (String email1, String email2){
        int id1 = getIntFromUsers(email1, "id");
        int id2 = getIntFromUsers(email2, "id");
        String query = "DELETE FROM connections WHERE ((connections.user1_id = ? AND connections.user2_id = ?) OR (connections.user1_id = ? AND connections.user2_id = ?))";
        String query1 = "DELETE FROM connectionRequests WHERE ((connectionRequests.sender_id = ? AND connectionRequests.receiver_id = ?) OR (connectionRequests.sender_id = ? AND connectionRequests.receiver_id = ?));";
        try (PreparedStatement statement = connection.prepareStatement(query);
             PreparedStatement statement1 = connection.prepareStatement(query1)){
            statement.setString(1, String.valueOf(id1));
            statement.setString(2, String.valueOf(id2));
            statement.setString(3, String.valueOf(id2));
            statement.setString(4, String.valueOf(id1));
            statement.executeUpdate();

            statement1.setString(1, String.valueOf(id1));
            statement1.setString(2, String.valueOf(id2));
            statement1.setString(3, String.valueOf(id2));
            statement1.setString(4, String.valueOf(id1));
            statement1.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteRequest (String email1, String email2){
        int id1 = getIntFromUsers(email1, "id");
        int id2 = getIntFromUsers(email2, "id");
        String query = "DELETE FROM connectionRequests WHERE ((connectionRequests.sender_id = ? AND connectionRequests.receiver_id) OR (connectionRequests.sender_id = ? AND connectionRequests.receiver_id));";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, String.valueOf(id1));
            statement.setString(2, String.valueOf(id2));
            statement.setString(3, String.valueOf(id2));
            statement.setString(4, String.valueOf(id1));
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<GetConnectionReturn> getLinkedInConnections (String userEmail) {
        int id = getIntFromUsers(userEmail, "id");
        ArrayList<GetConnectionReturn> connectedUsersInfo = new ArrayList<>();
        String query = "SELECT * FROM connectionRequests WHERE sender_id = ? OR receiver_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, String.valueOf(id));
            statement.setString(2, String.valueOf(id));
            try (ResultSet r = statement.executeQuery()) {
                while (r.next()){
                   if (r.getString("sender_id").equals(String.valueOf(id))){
                       String query2 = "SELECT * FROM users WHERE id = ?";
                       try (PreparedStatement statement1 = connection.prepareStatement(query2)) {
                           statement1.setString(1, r.getString("receiver_id"));
                           try(ResultSet result = statement1.executeQuery()) {
                               while (result.next()){
                                   String firstname = result.getString("firstname");
                                   String lastname = result.getString("lastname");
                                   String email = result.getString("email");
                                   String status = r.getString("status");
                                   String myRole = "sender";
                                   String note = r.getString("note");
                                   connectedUsersInfo.add(new GetConnectionReturn(firstname, lastname, email, status, myRole, note));
                               }
                           }
                       }
                   }
                   else {
                       String query2 = "SELECT * FROM users WHERE id = ?";
                       try (PreparedStatement statement1 = connection.prepareStatement(query2)) {
                           statement1.setString(1, r.getString("sender_id"));
                           try(ResultSet result = statement1.executeQuery()) {
                               while (result.next()){
                                   String firstname = result.getString("firstname");
                                   String lastname = result.getString("lastname");
                                   String email = result.getString("email");
                                   String status = r.getString("status");
                                   String myRole = "receiver";
                                   String note = r.getString("note");
                                   connectedUsersInfo.add(new GetConnectionReturn(firstname, lastname, email, status, myRole, note));
                               }
                           }
                       }
                   }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectedUsersInfo;
    }
    public void postThis(PostObject postObject){
        int userId = getIntFromUsers(postObject.getUserEmail(), "id");
        String videoPath = postObject.getVideoDestination();
        String imagePath = postObject.getImageDestination();
        String postText = postObject.getPostText();
        String query = "INSERT INTO posts (user_id, post_text, image, video) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, String.valueOf(userId));
            statement.setString(2, postText);
            statement.setString(3, imagePath);
            statement.setString(4, videoPath);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
