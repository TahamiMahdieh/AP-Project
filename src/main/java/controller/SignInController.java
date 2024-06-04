package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.common.*;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    private Socket socket = null;
    private ObjectOutputStream writer = null;
    private ObjectInputStream reader = null;
    private String jwt = null;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button signinButton;
    @FXML
    private Button signupButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView linkedInImage;
    @FXML
    private ImageView lockIconImage;
    @FXML
    void SignInButtonPressed(ActionEvent event) {
        if (emailTextField.getText().isBlank() || passwordTextField.getText().isBlank()){
            loginMessageLabel.setText("Please fill the blanks");
        }
        else {
            if (!isValidEmail(emailTextField.getText())){
                loginMessageLabel.setText("Invalid email. Please try again");
            }
            else if (!isValidPassword(passwordTextField.getText())){
                loginMessageLabel.setText("Invalid password. Please try again");
            }
            else { // this is when fields have been filled properly
                User u = new User(emailTextField.getText(), passwordTextField.getText());
                Bridge bridge = new Bridge(Commands.SIGN_IN, u, jwt);
                loginMessageLabel.setText("Loading ...");
                SendMessage.send(bridge, writer);
                startSignInTask();
            }
        }
    }
    @FXML
    void SignupButtonPressed(ActionEvent event) {
        Stage stage = (Stage) signupButton.getScene().getWindow();
        LinkedInApplication.showSignUpPage(stage,socket,writer,jwt);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File linkedInFile = new File("pictures/UserInterface/welcomePage.png");
        Image linkedIn = new Image(linkedInFile.toURI().toString());
        linkedInImage.setImage(linkedIn);
        File lockIconFile = new File("pictures/UserInterface/lock-icon-614x460.png");
        Image lockIcon = new Image(lockIconFile.toURI().toString());
        lockIconImage.setImage(lockIcon);
    }
    public void startSignInTask() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                runSignInTask();
            }
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }
    public void runSignInTask() {
        try {
            Bridge b = (Bridge) reader.readObject();
            if (b.getResponse() == Response.SUCCESSFUL_SIGN_IN && b.getCommand() == Commands.SIGN_IN) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        LinkedInApplication.setThisUserEmail(emailTextField.getText());
                        LinkedInApplication.showHomePage(jwt);
                    }
                });
            }
            else if (b.getResponse() == Response.FAILED_SIGN_IN_EMAIL_NOT_FOUND && b.getCommand() == Commands.SIGN_IN) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        loginMessageLabel.setText("Email not found. Please sign up first.");
                    }
                });
            }
            else if (b.getResponse() == Response.FAILED_SIGN_IN_WRONG_PASSWORD && b.getCommand() == Commands.SIGN_IN) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        loginMessageLabel.setText("Wrong password. Please try again");
                    }
                });
            }
            else if (b.getResponse() == Response.FAILED_SIGN_IN_INVALID_DATA && b.getCommand() == Commands.SIGN_IN) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        loginMessageLabel.setText("Please enter valid email and password.");
                    }
                });

            }
        }
        catch (ClassNotFoundException | IOException e){
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
    public String getLoginMessageLabel() {
        return loginMessageLabel.getText();
    }
    public void setLoginMessageLabel(String loginMessageLabel) {
        this.loginMessageLabel.setText(loginMessageLabel);
    }
    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public ObjectOutputStream getWriter() {
        return writer;
    }
    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }
    public String getJwt() {
        return jwt;
    }
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
    public ObjectInputStream getReader() {
        return reader;
    }
    public void setReader(ObjectInputStream reader) {
        this.reader = reader;
    }
}