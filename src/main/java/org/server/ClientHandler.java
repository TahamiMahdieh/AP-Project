package org.server;

import org.Database.DataBaseActions;
import org.Database.DatabaseConnection;
import org.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
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
                                String jwToken = JwtUtil.generateToken(user.getEmail());
                                result = new Bridge(Commands.SIGN_UP, Response.SUCCESSFUL_SIGNUP, user, jwToken);
                                result.setJwToken(jwToken);
                            }
                            else {
                                result = new Bridge (Commands.SIGN_UP, Response.FAILED_SIGNUP_DATABASE_FAILURE);
                            }
                        }
                        SendMessage.send(result, writer);
                    }
                    case SIGN_IN -> {
                        User user = bridge.get();
                        Bridge result = null;
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
