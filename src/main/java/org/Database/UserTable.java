package org.Database;


import org.common.SafeRunning;
import org.common.User;
import org.common.UserToBeSigned;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserTable extends Table{
    private static final String TABLE_NAME = "users";
    private static final String ID_COLUMN = "id";
    private static final String EMAIL_COLUMN = "email" ;
    private static final String FIRSTNAME_COLUMN = "firstname" ;
    private static final String LASTNAME_COLUMN = "lastname" ;
    private static final String PASSWORD_COLUMN = "password" ;

    public void createTable() {
        executeUpdate(  "CREATE TABLE IF NOT EXISTS " +TABLE_NAME+ " (" +
                ID_COLUMN + " INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                EMAIL_COLUMN + " VARCHAR(50), " +
                FIRSTNAME_COLUMN + " VARCHAR(20),  " +
                LASTNAME_COLUMN + " VARCHAR(20),  " +
                PASSWORD_COLUMN + " VARCHAR(40),  " +
                ")");
    }

    public void deleteTable() throws SQLException {
        String query = "DROP TABLE "+TABLE_NAME;
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }

    public synchronized boolean insert (UserToBeSigned user) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return SafeRunning.safe(() -> {
            String query = "INSERT INTO "  + TABLE_NAME + "(" +
                    EMAIL_COLUMN + ", " +
                    FIRSTNAME_COLUMN + ", " +
                    LASTNAME_COLUMN + ", " +
                    PASSWORD_COLUMN + ", " +
                    ") VALUES (?, ?, ?, ?)";

            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setString(1, user.getEmail().trim());
            statement.setString(2, user.getFirstName().trim());
            statement.setString(3, user.getPassword().trim());
            statement.executeUpdate();
            statement.close();
        });
    }

    public synchronized void updateUsername(int userDbId, String newUsername) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_USERNAME + " = '"+newUsername+"'" + " WHERE "+ COLUMN_ID + " = '"+userDbId+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        int set = statement.executeUpdate();
//        set.close();
        FollowTable followTable = new FollowTable();
        followTable.updateUsername(userDbId,newUsername);
        BlockTable blockTable = new BlockTable();
        blockTable.updateUsername(userDbId,newUsername);
    }
    public synchronized void updatePassword(int userDbId, String newPassword) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_PASSWORD + " = '"+newPassword+"'" + " WHERE "+ COLUMN_ID + " = '"+userDbId+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        int set = statement.executeUpdate();
//        set.close();
    }
    public synchronized void updateFirstName(int userDbId, String newFirstName) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_FIRSTNAME + " = '"+newFirstName+"'" + " WHERE "+ COLUMN_ID + " = '"+userDbId+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        int set = statement.executeUpdate();
//        set.close();
    }
    public synchronized void updateLastName(int userDbId, String newLastName) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_LASTNAME + " = '"+newLastName+"'" + " WHERE "+ COLUMN_ID + " = '"+userDbId+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        int set = statement.executeUpdate();
//        set.close();
    }
    public synchronized void updatePhoneNumber(int userDbId, String newPhoneNumber) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_PHONE_NUMBER + " = '"+newPhoneNumber+"'" + " WHERE "+ COLUMN_ID + " = '"+userDbId+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        int set = statement.executeUpdate();
//        set.close();
    }
    public synchronized void updateEmail(int userDbId, String newEmail) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_EMAIL + " = '"+newEmail+"'" + " WHERE "+ COLUMN_ID + " = '"+userDbId+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        int set = statement.executeUpdate();
//        set.close();
    }
    public synchronized void updateAvatar(int userDbId, String newAvatar) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_AVATAR + " = '"+newAvatar+"'" + " WHERE "+ COLUMN_ID + " = '"+userDbId+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        int set = statement.executeUpdate();
//        set.close();
    }
    public synchronized void updateHeader(int userDbId, String newHeader) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_HEADER + " = '"+newHeader+"'" + " WHERE "+ COLUMN_ID + " = '"+userDbId+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        int set = statement.executeUpdate();
//        set.close();
    }
    public synchronized void updateRegion(int userDbId, String newRegion) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_REGION + " = '"+newRegion+"'" + " WHERE "+ COLUMN_ID + " = '"+userDbId+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        int set = statement.executeUpdate();
//        set.close();
    }
    //Reminder: check if the date works like this
    public synchronized void updateBirthDate(int userDbId, Date newDate) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_BIRTHDATE + " = '"+newDate+"'" + " WHERE "+ COLUMN_ID + " = '"+userDbId+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        int set = statement.executeUpdate();
//        set.close();
    }
    // I think this is useless that I saved the sign up date in the table creation
//    public synchronized void updateSignUp(String username, Date newSignUpDate) throws SQLException{
//        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_SIGNUPDATE + " = '"+newSignUpDate+"'" + " WHERE "+ COLUMN_USERNAME + " = '"+username+"'";
//        PreparedStatement statement = getConnection().prepareStatement(query);
//        ResultSet set = statement.executeQuery();
//        set.close();
//    }
    public synchronized void updateLastModifiedDate(int userDbId, Date newLastModifiedDate) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_LASTMODIFIEDDATE + " = '"+newLastModifiedDate+"'" + " WHERE "+ COLUMN_ID + " = '"+userDbId+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
    }
    public synchronized void updateBio(int userDbId, String newBio) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_BIO + " = '"+newBio+"'" + " WHERE "+ COLUMN_ID + " = '"+userDbId+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        int set = statement.executeUpdate();
//        set.close();
    }
    public synchronized void updateLocation(int userDbId, String newLocation) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_LOCATION + " = '"+newLocation+"'" + " WHERE "+ COLUMN_ID + " = '"+userDbId+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        int set = statement.executeUpdate();
//        set.close();
    }
    public synchronized void updateWebsite(int userDbId, String newWebsite) throws SQLException{
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_WEBSITE + " = '"+newWebsite+"'" + " WHERE "+ COLUMN_ID + " = '"+userDbId+"'";
        PreparedStatement statement = getConnection().prepareStatement(query);
        int set = statement.executeUpdate();
//        set.close();
    }
    public <T> T select(UserToBeSigned userModel) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_USERNAME + "=?";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, userModel.getUsername().trim());
        ResultSet set = statement.executeQuery();
        UserToBeSigned out = null;
        if (set.next()){
            if (set.getString(COLUMN_PASSWORD).equals(userModel.getPassword())){

                out = new UserToBeSigned();
                out.setUsername(userModel.getUsername());
                out.setFirstName(set.getString(COLUMN_FIRSTNAME));
                out.setLastName(set.getString(COLUMN_LASTNAME));
                out.setEmail(set.getString(COLUMN_EMAIL));


                set.close();
                statement.close();
            }else {
                return (T) ResponseOrErrorType.INVALID_PASS;
            }
        }else {
            return (T) ResponseOrErrorType.USER_NOTFOUND;
        }
        return (T) out;
    }

    public synchronized boolean userNameExists(String userName) throws SQLException {
        String query = "SELECT " + COLUMN_LASTNAME + " FROM " + TABLE_NAME + " WHERE " +
                COLUMN_USERNAME + "=? LIMIT 1";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, userName.trim());
        ResultSet set = statement.executeQuery();

        boolean exists = set.next();
        set.close();
        statement.close();
        return exists;
    }
    public synchronized boolean emailExists(String email) throws SQLException {
        String query = "SELECT " + COLUMN_USERNAME + " FROM " + TABLE_NAME + " WHERE " +
                COLUMN_EMAIL + "=? LIMIT 1";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, email.trim());
        ResultSet set = statement.executeQuery();

        boolean exists = set.next();
        set.close();
        statement.close();
        return exists;
    }
    public synchronized boolean phoneNumberExists(String phoneNumber) throws SQLException {
        String query = "SELECT " + COLUMN_USERNAME + " FROM " + TABLE_NAME + " WHERE " +
                COLUMN_PHONE_NUMBER + "=? LIMIT 1";

        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, phoneNumber.trim());
        ResultSet set = statement.executeQuery();

        boolean exists = set.next();
        set.close();
        statement.close();
        return exists;
    }
    public synchronized HashSet<User> searchInUsers(String searchKey) throws SQLException {
//        String query1 = "SELECT * FROM " + TABLE_NAME +  " WHERE " + COLUMN_USERNAME + " LIKE " + "'%" + searchKey + "%'" ;
//        String query2 = "SELECT * FROM " + TABLE_NAME +  " WHERE " + COLUMN_FIRSTNAME + " LIKE " + "'%" + searchKey + "%'" ;
//        String query3 = "SELECT * FROM " + TABLE_NAME +  " WHERE " + COLUMN_LASTNAME + " LIKE " + "'%" + searchKey + "%'" ;

        String query = "SELECT * FROM " + TABLE_NAME;
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
//        PreparedStatement statement1 = getConnection().prepareStatement(query1);
//        PreparedStatement statement2 = getConnection().prepareStatement(query2);
//        PreparedStatement statement3 = getConnection().prepareStatement(query3);
//        ResultSet set1 = statement1.executeQuery();
//        ResultSet set2 = statement2.executeQuery();
//        ResultSet set3 = statement3.executeQuery();
        HashSet<User> users = new HashSet<>();
        while (set.next()){
            if (set.getString("username").toLowerCase().contains(searchKey.toLowerCase()) ||
                    set.getString("first_name").toLowerCase().contains(searchKey.toLowerCase()) ||
                    set.getString("last_name").toLowerCase().contains(searchKey.toLowerCase())){
                User user = new User(set.getString("username"),
                        null,
                        set.getString("first_name"),
                        set.getString("last_name"),
                        null,
                        null,
                        set.getString("avatar"),
                        set.getString("header"),
                        null//set1.getString("country")
                        , //TODO
                        set.getDate("birthdate"),
                        set.getDate("signUpDate"),
                        set.getDate("lastModifiedDate"), //TODO sth about showing dates in prof of others
                        set.getString("bio"),
                        set.getString("location"),
                        set.getString("website"));
                FollowTable followTable = SQLConnection.getFollowTable();
                user.setFollowers(followTable.getFollowers(user.getUsername()));
                user.setFollowings(followTable.getFollowings(user.getUsername()));
                user.setBlockings(SQLConnection.getBlockTable().getBlockings(user.getUsername()));
                user.setBlocker(SQLConnection.getBlockTable().getBlockers(user.getUsername()));
                user.setDatabaseId(set.getInt("id"));
                users.add(user);
            }
        }
//        while (set1.next()){
//            User user = new User(set1.getString("username"),
//                    null,
//                    set1.getString("first_name"),
//                    set1.getString("last_name"),
//                    null,
//                    null,
//                    set1.getString("avatar"),
//                    set1.getString("header"),
//                    null//set1.getString("country")
//                     , //TODO
//                    set1.getDate("birthdate"),
//                    set1.getDate("signUpDate"),
//                    set1.getDate("lastModifiedDate"),
//                    set1.getString("bio"),
//                    set1.getString("location"),
//                    set1.getString("website"));
//            FollowTable followTable = SQLConnection.getFollowTable();
//            user.setFollowers(followTable.getFollowers(user.getUsername()));
//            user.setFollowings(followTable.getFollowings(user.getUsername()));
//            user.setBlackList(SQLConnection.getBlockTable().getBlockings(user.getUsername()));
//            user.setDatabaseId(set1.getInt("id"));
//            users.add(user);
//        }
//        while (set2.next()){
//            User user = new User(set2.getString("username"),
//                    null,
//                    set2.getString("first_name"),
//                    set2.getString("last_name"),
//                    null,
//                    null,
//                    set2.getString("avatar"),
//                    set2.getString("header"),
//                    null//set1.getString("country")
//                    , //TODO
//                    set2.getDate("birthdate"),
//                    set2.getDate("signUpDate"),
//                    set2.getDate("lastModifiedDate"),
//                    set2.getString("bio"),
//                    set2.getString("location"),
//                    set2.getString("website"));
//            FollowTable followTable = SQLConnection.getFollowTable();
//            user.setFollowers(followTable.getFollowers(user.getUsername()));
//            user.setFollowings(followTable.getFollowings(user.getUsername()));
//            user.setBlackList(SQLConnection.getBlockTable().getBlockings(user.getUsername()));
//            user.setDatabaseId(set2.getInt("id"));
//            users.add(user);
//        }
//        while (set3.next()){
//            User user = new User(set3.getString("username"),
//                    null,
//                    set3.getString("first_name"),
//                    set3.getString("last_name"),
//                    null,
//                    null,
//                    set3.getString("avatar"),
//                    set3.getString("header"),
//                    null//set1.getString("country")
//                    , //TODO
//                    set3.getDate("birthdate"),
//                    set3.getDate("signUpDate"),
//                    set3.getDate("lastModifiedDate"),
//                    set3.getString("bio"),
//                    set3.getString("location"),
//                    set3.getString("website"));
//            FollowTable followTable = SQLConnection.getFollowTable();
//            user.setFollowers(followTable.getFollowers(user.getUsername()));
//            user.setFollowings(followTable.getFollowings(user.getUsername()));
//            user.setBlackList(SQLConnection.getBlockTable().getBlockings(user.getUsername()));
//            user.setDatabaseId(set3.getInt("id"));
//            users.add(user);
//        }
//        set1.close();
//        set2.close();
//        set3.close();
//        statement1.close();
//        statement2.close();
//        statement3.close();
        return users;
    }
    public User getUserFromDatabase(String username) throws SQLException {
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE " + COLUMN_USERNAME + " = '"+username+"'" ;
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.next();
        User user = new User(set.getString("username"),
                set.getString("password"),
                set.getString("first_name"),
                set.getString("last_name"),
                set.getString("email"),
                set.getString("phone_number"),
                set.getString("avatar"),
                set.getString("header"),
                set.getString("country"),
                set.getDate("birthdate"),
                set.getDate("signUpDate"),
                set.getDate("lastModifiedDate"),
                set.getString("bio"),
                set.getString("location"),
                set.getString("website"));
        FollowTable followTable = SQLConnection.getFollowTable();
        user.setFollowers(followTable.getFollowers(username));
        user.setFollowings(followTable.getFollowings(username));
        user.setBlockings(SQLConnection.getBlockTable().getBlockings(username));
        user.setDatabaseId(set.getInt("id"));
        return user;
    }
}




}
