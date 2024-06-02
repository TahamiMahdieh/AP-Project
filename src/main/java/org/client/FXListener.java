package org.client;

import controller.LinkedInApplication;
import controller.SignInController;
import javafx.stage.Stage;
import org.common.Bridge;
import org.common.Response;
import org.common.SendMessage;
import org.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class FXListener implements Runnable{
    private Socket socket;
    private static MainClient instance;
    private ObjectInputStream reader = null;
    private ObjectOutputStream writer = null;
    private String jwToken;
    private Stage stage;
    private SignInController controller;

    public FXListener(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream, Stage stage) throws IOException {
        this.writer = outputStream;
        this.reader = inputStream;
        this.socket = socket;
        this.stage = stage;
        this.jwToken = null;
    }


    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                Bridge b = (Bridge) reader.readObject();
                switch (b.getCommand()) {
                    case SIGN_IN -> {
                        if (b.getResponse() == Response.SUCCESSFUL_SIGN_IN) {
                            LinkedInApplication.setThisUser((User) b.getMessage());
                            this.jwToken = b.getJwToken();
                            LinkedInApplication.showHomePage(stage, socket,writer, jwToken);
                        }
                        else {
                            LinkedInApplication.showSignInPage(stage, socket, writer, jwToken, "You don't have an account. Please signup first");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Socket getSocket() {
        return socket;
    }
    public static MainClient getInstance() {
        return instance;
    }
    public ObjectInputStream getReader() {
        return reader;
    }
    public ObjectOutputStream getWriter() {
        return writer;
    }
    public String getJwToken() {
        return jwToken;
    }
    public void setJwToken(String jwToken) {
        this.jwToken = jwToken;
    }
    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public SignInController getController() {
        return controller;
    }
    public void setController(SignInController controller) {
        this.controller = controller;
    }
}
