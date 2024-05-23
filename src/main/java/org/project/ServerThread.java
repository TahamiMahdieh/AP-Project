package org.project;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable{
    private Socket socket;
    private ObjectInputStream input = null;
    private ObjectOutputStream output = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
        try {
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException e) {
            System.out.println("Creating stream failed!!");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                Bridge b = (Bridge) input.readObject();
                switch (b.getCommand()) {
                    case SIGN_UP:
                        User temp = (User) b.getMessage();
                        if (isValidEmail(temp.getEmail()) && isValidName(temp.getFirstname()) && isValidName(temp.getLastname()) && isValidPassword(temp.getPassword())) {
                            //TODO : add user to mySQL
                            Bridge bridge =
                        }
                        else {
                            Bridge bridge = new Bridge(Errors.FAILED_SIGNUP);
                            output.writeObject(bridge);
                        }
                        break;
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email != null && email.matches(emailRegex);
    }
    private boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z]+");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.matches(".*[a-zA-Z].*") && password.matches(".*[0-9].*");
    }
}
