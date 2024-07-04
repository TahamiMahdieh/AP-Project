package org.Database;

import org.common.*;

import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;


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

    public String getCountry(String email) {
        return getStringFromUserProfile(email, "country");
    }

    public boolean setCountry(String email, String newCountry) {
        return setStringToUserProfile(email, "country", newCountry);
    }

    public String getCity(String email) {
        return getStringFromUserProfile(email, "city");
    }

    public boolean setCity(String email, String newCity) {
        return setStringToUserProfile(email, "city", newCity);
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

    private boolean setStringToContactInfo(String email, String label, String newStr) {
        String query = "UPDATE contact_info SET " + label + " = \"" + newStr + "\" WHERE email = ?;";
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

//    public boolean setDateToContactInfo(String email, String label, Date date) {
//
//    }


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

    public boolean addEducation(String email, String schoolName, String fieldOfStudy, Date startDate, Date endDate, double grade, String activitiesAndSocieties, String description, String skills, boolean notifyNetwork) {
        try {
            connection.setAutoCommit(false);

            String insertIntoEducation = "INSERT INTO education (school_name, field_of_study, start_date, end_date, grade, activities_and_societies, edu_description, skills, notify_network) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            int education_id = insertAndGetId(connection, insertIntoEducation, schoolName, fieldOfStudy, startDate, endDate, grade, activitiesAndSocieties, description, skills, notifyNetwork);
            int user_profile_id = getIntFromUsers(email, "user_profile_id");

            String insertIntoJunction = "INSERT INTO user_profile_edu_junction (user_profile_id, education_id) VALUES (?, ?);";
            insertWithTwoIds(connection, insertIntoJunction, user_profile_id, education_id);

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

    public ArrayList<Education> getEducations(String email) {
        ArrayList<Education> educations = new ArrayList<>();
        int user_profile_id = getIntFromUsers(email, "user_profile_id");
        String query = "SELECT * FROM user_profile_edu_junction WHERE user_profile_id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(user_profile_id));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int education_id = resultSet.getInt("education_id");
                educations.add(getEducation(email, education_id));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return educations;
    }

    public Education getEducation(String email, int education_id) {
        String query = "SELECT * FROM education WHERE id = ?;";
        Education education;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, education_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ArrayList<String> skills = null;
                if (!(resultSet.getString("skills") == null))
                    skills = new ArrayList<>(List.of(resultSet.getString("skills").split(",")));

                education = new Education(email,
                        resultSet.getString("school_name"),
                        resultSet.getString("field_of_study"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getDouble("grade"),
                        resultSet.getString("activities_and_societies"),
                        resultSet.getString("edu_description"),
                        skills,
                        resultSet.getBoolean("notify_network"));
                education.setEducationId(resultSet.getString("id"));

                return education;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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
    public void commentThis (CommentObject commentObject){
        int userId = getIntFromUsers(commentObject.getCommentMakerEmail(), "id");
        String videoPath = commentObject.getVideoDestination();
        String imagePath = commentObject.getImageDestination();
        String postText = commentObject.getCommentText();
        String postId = commentObject.getPostId();
        String query = "INSERT INTO comments (user_id, post_id, comment_text, image, video) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, String.valueOf(userId));
            statement.setString(2, postId);
            statement.setString(3, postText);
            statement.setString(4, imagePath);
            statement.setString(5, videoPath);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<CommentObject> getCommentObjects(PostObject postObject){
        ArrayList<CommentObject> commentObjects = new ArrayList<>();
        String postId = postObject.getPostId();
        String query = "SELECT * FROM comments WHERE post_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, postId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                CommentObject commentObject = new CommentObject();
                commentObject.setCommentText(resultSet.getString("comment_text"));
                commentObject.setCommentMakerEmail(getEmailUsingId(resultSet.getString("user_id")));
                commentObject.setCommentMakerName(getNameUsingId(resultSet.getString("user_id")));
                commentObject.setPostId(postId);
                commentObject.setImageDestination(resultSet.getString("image"));
                commentObject.setVideoDestination(resultSet.getString("video"));
                commentObjects.add(commentObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentObjects;
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
    public ArrayList<String> getFollowersIdUsingEmail(String followeeEmail){
        ArrayList<String> followersEmail = new ArrayList<>();
        int followeeId = getIntFromUsers(followeeEmail, "id");
        String query = "SELECT email FROM users JOIN follows ON users.id = follows.follower_id WHERE follows.followee_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, String.valueOf(followeeId));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    followersEmail.add(resultSet.getString("id"));
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
    public ArrayList<String> getFolloweeIdsUsingEmail(String followerEmail){
        ArrayList<String> followeeEmails = new ArrayList<>();
        int followerId = getIntFromUsers(followerEmail, "id");
        String query = "SELECT * FROM users JOIN follows ON users.id = follows.followee_id WHERE follows.follower_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, String.valueOf(followerId));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    followeeEmails.add(resultSet.getString("id"));
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
    public ArrayList<String> getUsersConnectedWithThis (String email){
        int myId = getIntFromUsers(email, "id");
        ArrayList<String> connectedIds = new ArrayList<>();
        String query = "SELECT * FROM connections WHERE user1_id = ? OR user2_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, String.valueOf(myId));
            statement.setString(2, String.valueOf(myId));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                if (resultSet.getString("user2_id").equals(String.valueOf(myId))){
                    connectedIds.add(resultSet.getString("user1_id"));
                }
                else {
                    connectedIds.add(resultSet.getString("user2_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectedIds;
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
        int userId = getIntFromUsers(postObject.getPostMakerEmail(), "id");
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
    public void deleteMyPost(PostObject postObject){
        String query = "DELETE FROM posts WHERE posts.post_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, postObject.getPostId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<PostObject> getMyPosts(String email){
        ArrayList<PostObject> postObjects = new ArrayList<>();
        int id = getIntFromUsers(email, "id");
        String query = "SELECT * FROM posts WHERE posts.user_id = ? ;";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, String.valueOf(id));
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    PostObject p = new PostObject();
                    p.setPostText(resultSet.getString("post_text"));
                    p.setVideoDestination(resultSet.getString("video"));
                    p.setImageDestination(resultSet.getString("image"));
                    p.setPostMakerEmail(email);
                    p.setPostId(resultSet.getString("post_id"));
                    postObjects.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postObjects;
    }
    public ArrayList<PostObject> getMyHashtagPosts(String email, String hashtagWord){
        ArrayList<PostObject> postObjects = new ArrayList<>();
        int id = getIntFromUsers(email, "id");
        String query = "SELECT * FROM posts WHERE posts.user_id = ? AND post_text LIKE ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, String.valueOf(id));
            statement.setString(2, "%" + hashtagWord + "%");
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    PostObject p = new PostObject();
                    p.setPostText(resultSet.getString("post_text"));
                    p.setVideoDestination(resultSet.getString("video"));
                    p.setImageDestination(resultSet.getString("image"));
                    p.setPostMakerEmail(email);
                    p.setPostId(resultSet.getString("post_id"));
                    postObjects.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postObjects;
    }
    public ArrayList<PostObject> getOthersPosts(String myEmail){
        ArrayList<PostObject> othersPosts = new ArrayList<>();
        ArrayList<String> others = getFolloweeIdsUsingEmail(myEmail);
        others.addAll(getUsersConnectedWithThis(myEmail));
        String inClause = String.join(",", others.stream().map(id -> "?").toArray(String[]::new));
        String query = "SELECT * FROM posts WHERE user_id IN (" + inClause + ");";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            for (int i = 0; i < others.size(); i++) {
                statement.setString(i + 1, others.get(i));
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                PostObject p = new PostObject();
                p.setPostText(resultSet.getString("post_text"));
                p.setVideoDestination(resultSet.getString("video"));
                p.setImageDestination(resultSet.getString("image"));
                p.setPostMakerEmail(getEmailUsingId(resultSet.getString("user_id")));
                p.setPostId(resultSet.getString("post_id"));
                p.setPostMakerName(getNameUsingId(resultSet.getString("user_id")));
                othersPosts.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return othersPosts;
    }
    public  ArrayList<PostObject> getOthersHashtagPosts(String email, String hashtagWord){
        ArrayList<PostObject> othersPosts = new ArrayList<>();
        int myId = getIntFromUsers(email, "id");
        String query = "SELECT * FROM posts WHERE user_id != ? AND post_text LIKE ? ;";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, String.valueOf(myId));
            statement.setString(2, "%" + hashtagWord + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                PostObject p = new PostObject();
                p.setPostText(resultSet.getString("post_text"));
                p.setVideoDestination(resultSet.getString("video"));
                p.setImageDestination(resultSet.getString("image"));
                p.setPostMakerEmail(getEmailUsingId(resultSet.getString("user_id")));
                p.setPostId(resultSet.getString("post_id"));
                p.setPostMakerName(getNameUsingId(resultSet.getString("user_id")));
                othersPosts.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return othersPosts;
    }
    public void deleteLike(PostObject postObject){
        int userId = getIntFromUsers(postObject.getPostVisitor(), "id");
        String query = "DELETE FROM likes WHERE (user_id = ? AND post_id = ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, String.valueOf(userId));
            statement.setString(2, postObject.getPostId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addLike (PostObject postObject){
        int userId = getIntFromUsers(postObject.getPostVisitor(), "id");
        String query = "INSERT INTO likes (user_id, post_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, String.valueOf(userId));
            statement.setString(2, postObject.getPostId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean hasLiked(PostObject postObject){
        boolean hasLiked;
        int userId = getIntFromUsers(postObject.getPostVisitor(), "id");
        String query = "SELECT * FROM likes WHERE user_id = ? AND post_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, String.valueOf(userId));
            statement.setString(2, postObject.getPostId());
            ResultSet resultSet = statement.executeQuery();
            hasLiked = resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hasLiked;
    }
    public ArrayList<String> getWhoHasLiked (PostObject postObject){
        ArrayList<String> likes = new ArrayList<>();
        String query = "SELECT * FROM likes JOIN users ON users.id = likes.user_id WHERE post_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, postObject.getPostId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                likes.add(resultSet.getString("firstname") + " " + resultSet.getString("lastname") + " -> " + resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likes;
    }
    public String getEmailUsingId (String id){
        String email = null;
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                email = resultSet.getString("email");
            }
            return email;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }
    public String getNameUsingId (String id){
        String name = null;
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                name = resultSet.getString("firstname") + " " + resultSet.getString("lastname");
            }
            return name;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
    private static void insertWithTwoIds(Connection conn, String sql, long id1, long id2) {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id1);
            stmt.setLong(2, id2);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEducation(Education education) {
        String educationId = education.getEducationId();
        String query1 = "DELETE FROM education WHERE id = ?";
        String query2 = "DELETE FROM user_profile_edu_junction WHERE education_id = ?";
        try(PreparedStatement statement1 = connection.prepareStatement(query1);
            PreparedStatement statement2 = connection.prepareStatement(query2)){
            statement1.setString(1, educationId);
            statement2.setString(1, educationId);
            statement2.executeUpdate();
            statement1.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
