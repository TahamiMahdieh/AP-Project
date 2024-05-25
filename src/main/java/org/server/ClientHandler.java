package org.server;

import org.Database.DataBaseActions;
import org.Database.DatabaseConnection;
import org.common.Bridge;
import org.common.Response;
import org.common.User;

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
                        // invalid inputs. sign up failed
                        if (!isValidEmail(user.getEmail()) || !isValidName(user.getFirstname()) || !isValidName(user.getLastname()) || !isValidPassword(user.getPassword())){
                            Bridge failedSignUp = new Bridge(Response.FAILED_SIGNUP_INVALID_DATA);
                            writer.writeObject(failedSignUp);
                        }
                        // check if email already exists
                        else if (dataBaseActions.doesEmailExist()){
                            Bridge failedSignUp = new Bridge(Response.FAILED_SIGNUP_DUPLICATED_EMAIL);

                        }
                        // user can be signed up now
                        else {

                        }
                        System.out.println("okkkkkkkkkkkkkkkkk");
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
