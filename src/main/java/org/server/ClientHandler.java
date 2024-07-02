package org.server;

import org.Database.DataBaseActions;
import org.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket connectionSocket;
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private ObjectOutputStream writer = null;
    private ObjectInputStream reader = null;

    public ClientHandler(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
        try{
            writer = new ObjectOutputStream(connectionSocket.getOutputStream());
            reader = new ObjectInputStream(connectionSocket.getInputStream());
        }
        catch (IOException io){
            io.printStackTrace();
        }
        clientHandlers.add(this);
    }

    @Override
    public void run (){
        DataBaseActions dataBaseActions = new DataBaseActions();
        try {
            while (connectionSocket.isConnected()){
                Bridge bridge = (Bridge) reader.readObject();
                switch (bridge.getCommand()){
                    case SIGN_UP -> {
                        User user = bridge.get();
                        Bridge result = null;
                        // invalid inputs. sign up failed
                        if (!isValidEmail(user.getEmail()) || !isValidName(user.getFirstname()) || !isValidName(user.getLastname()) || !isValidPassword(user.getPassword())){
                            result = new Bridge(Commands.SIGN_UP, Response.FAILED_SIGNUP_INVALID_DATA);
                        }
                        // check if email already exists
                        else if (dataBaseActions.doesEmailExist(user.getEmail())){
                            result = new Bridge(Commands.SIGN_UP, Response.FAILED_SIGNUP_DUPLICATED_EMAIL);
                        }
                        // user can be signed up now
                        else {
                            boolean done = dataBaseActions.addUserToUsersTable(user);
                            if (done){
                                //String jwToken = JwtUtil.generateToken(user.getEmail());
                                result = new Bridge(Commands.SIGN_UP, Response.SUCCESSFUL_SIGNUP, user, null);
                                result.setJwToken(null);
                            }
                            else {
                                result = new Bridge (Commands.SIGN_UP, Response.FAILED_SIGNUP_DATABASE_FAILURE);
                            }
                        }
                        SendMessage.send(result, writer);
                    }
                    case SIGN_IN -> {
                        User user = bridge.get();
                        Bridge result;
                        if (!isValidEmail(user.getEmail()) || !isValidPassword(user.getPassword())){
                            result = new Bridge(Commands.SIGN_IN, Response.FAILED_SIGN_IN_INVALID_DATA);
                        }
                        else if (!dataBaseActions.doesEmailExist(user.getEmail())) {
                            result = new Bridge(Commands.SIGN_IN, Response.FAILED_SIGN_IN_EMAIL_NOT_FOUND);
                        }
                        else if (!dataBaseActions.checkPassword(user.getEmail(), user.getPassword())) {
                            result = new Bridge(Commands.SIGN_IN, Response.FAILED_SIGN_IN_WRONG_PASSWORD);
                        }
                        else {
                            result = new Bridge(Commands.SIGN_IN, Response.SUCCESSFUL_SIGN_IN);
                        }
                        SendMessage.send(result, writer);
                    }
                    case SEARCH -> {
                        String phrase = bridge.get();
                        ArrayList<String> found = dataBaseActions.search(phrase);
                        Bridge b = new Bridge(Commands.SEARCH, found);
                        SendMessage.send(b, writer);
                    }
                    case HOMEPAGE_INFORMATION_LISTVIEW -> {
                        String email = bridge.get();
                        ArrayList<String> info = new ArrayList<>();
                        info.add(dataBaseActions.getFirstname(email));
                        info.add(dataBaseActions.getLastname(email));
                        info.add(dataBaseActions.getHeadline(email));
                        info.add(dataBaseActions.getProfilePicture(email));
                        Bridge b = new Bridge(Commands.HOMEPAGE_INFORMATION_LISTVIEW, info);
                        SendMessage.send(b, writer);
                    }
                    case GET_FOLLOWEE -> {
                        String email = bridge.get();
                        ArrayList<String> followee = dataBaseActions.getFolloweeInfoUsingEmail(email);
                        Bridge b = new Bridge(Commands.GET_FOLLOWEE, followee);
                        SendMessage.send(b, writer);
                    }
                    case GET_FOLLOWERS -> {
                        String email = bridge.get();
                        ArrayList<String> followers = dataBaseActions.getFollowersInfoUsingEmail(email);
                        Bridge b = new Bridge(Commands.GET_FOLLOWERS, followers);
                        SendMessage.send(b, writer);
                    }
                    case GET_CONNECTION -> {
                        String email = bridge.get();
                        ArrayList<GetConnectionReturn> connection = dataBaseActions.getLinkedInConnections(email);
                        Bridge b = new Bridge(Commands.GET_CONNECTION, connection);
                        SendMessage.send(b, writer);
                    }
                    case POST_THIS -> {
                        PostObject postObject = bridge.get();
                        if (postObject.getImageFile() != null) {
                            String fileName = postObject.getImageFile().getName();
                            Path destination = Path.of("pictures/postImages", fileName);
                            try {
                                Files.copy(postObject.getImageFile().toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
                                postObject.setImageDestination(destination.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (postObject.getVideoFile() != null) {
                            String fileName2 = postObject.getVideoFile().getName();
                            Path destination2 = Path.of("pictures/postVideos", fileName2);
                            try {
                                Files.copy(postObject.getVideoFile().toPath(), destination2, StandardCopyOption.REPLACE_EXISTING);
                                postObject.setVideoDestination(destination2.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        dataBaseActions.postThis(postObject);
                    }
                    case SHOW_MY_POSTS -> {
                        String email = bridge.get();
                        ArrayList<PostObject> postsArray = dataBaseActions.getMyPosts(email);
                        Bridge b = new Bridge(Commands.SHOW_MY_POSTS, postsArray);
                        SendMessage.send(b, writer);
                    }
                    case SHOW_OTHERS_POSTS -> {
                        String email = bridge.get();
                        ArrayList<PostObject> postsArray = dataBaseActions.getOthersPosts(email);
                        Bridge b = new Bridge(Commands.SHOW_OTHERS_POSTS, postsArray);
                        SendMessage.send(b, writer);
                    }
                    case IS_ALREADY_LIKED -> {
                        PostObject postObject = bridge.get();
                        Boolean aBoolean = dataBaseActions.hasLiked(postObject);
                        Bridge b = new Bridge(Commands.IS_ALREADY_LIKED, aBoolean);
                        SendMessage.send(b, writer);
                    }
                    case DELETE_MY_POST -> {
                        PostObject postObject = bridge.get();
                        dataBaseActions.deleteMyPost(postObject);
                    }
                    case ADD_LIKE -> {
                        PostObject postObject = bridge.get();
                        dataBaseActions.addLike(postObject);
                    }
                    case DELETE_LIKE -> {
                        PostObject postObject = bridge.get();
                        dataBaseActions.deleteLike(postObject);
                    }
                    case SEE_LIKES_LIST -> {
                        PostObject postObject = bridge.get();
                        ArrayList<String> likes = dataBaseActions.getWhoHasLiked(postObject);
                        Bridge b = new Bridge(Commands.SEE_LIKES_LIST, likes);
                        SendMessage.send(b, writer);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void write(Bridge bridge) {
        if (bridge == null) return;
        try {
            writer.writeObject(bridge);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email != null && email.matches(emailRegex);
    }
    private static boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z]+");
    }

    private static boolean isValidPassword(String password) {
        return password != null && password.length()>= 8 && password.matches(".*[a-zA-Z].*") && password.matches(".*[0-9].*");
    }
}
