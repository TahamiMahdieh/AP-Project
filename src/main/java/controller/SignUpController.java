package controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.common.*;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    private Socket socket = null;
    private ObjectOutputStream writer = null;
    private ObjectInputStream reader = null;
    private String jwt = null;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private ImageView linkedInImage;
    @FXML
    private ImageView keyImage;
    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField repeatPasswordTextField;

    @FXML
    private Button signUpButton;

    @FXML
    private Label signUpMessageLabel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File linkedInFile = new File("pictures/UserInterface/welcomePage.png");
        Image linkedIn = new Image(linkedInFile.toURI().toString());
        linkedInImage.setImage(linkedIn);
        File keyIconFile = new File("pictures/UserInterface/key-icon.png");
        Image keyIcon = new Image(keyIconFile.toURI().toString());
        keyImage.setImage(keyIcon);
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        LinkedInApplication.showSignInPage(jwt);
    }

    @FXML
    void signUpButtonPressed(ActionEvent event) {
        if (emailTextField.getText().isBlank() || passwordTextField.getText().isBlank() || repeatPasswordTextField.getText().isBlank() || firstNameTextField.getText().isBlank() || lastNameTextField.getText().isBlank()){
            signUpMessageLabel.setText("Please fill the blanks");
        }
        else {
            if (!isValidEmail(emailTextField.getText())){
                signUpMessageLabel.setText("Invalid email. Please try again");
            }
            else if (!isValidPassword(passwordTextField.getText())){
                signUpMessageLabel.setText("Invalid password. Please try again");
            }
            else if (!isValidName(firstNameTextField.getText()) || !isValidName(lastNameTextField.getText())){
                signUpMessageLabel.setText("Invalid name. Please try again");
            }
            else if (!passwordTextField.getText().equals(repeatPasswordTextField.getText())){
                signUpMessageLabel.setText("Password doesn't match the repetition.\n                Please try again");
            }
            else { // this is when fields have been filled properly
                User u = new User(emailTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), passwordTextField.getText());
                Bridge bridge = new Bridge(Commands.SIGN_UP, u, jwt);
                signUpMessageLabel.setText("Loading ...");
                SendMessage.send(bridge, writer);
                startSignUpTask();
            }
        }
    }

    public void startSignUpTask() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                runSignUpTask();
            }
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }
    public void runSignUpTask() {
        try {
            Bridge b = (Bridge) reader.readObject();
            if (b.getResponse() == Response.SUCCESSFUL_SIGNUP && b.getCommand() == Commands.SIGN_UP) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        LinkedInApplication.setThisUserEmail(emailTextField.getText());
                        LinkedInApplication.showHomePage(jwt);
                    }
                });
            }
            else if (b.getResponse() == Response.FAILED_SIGNUP_DUPLICATED_EMAIL && b.getCommand() == Commands.SIGN_UP) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        signUpMessageLabel.setText("This email already exists.\n Please try another one.");
                    }
                });

            }
            else if (b.getResponse() == Response.FAILED_SIGNUP_DATABASE_FAILURE && b.getCommand() == Commands.SIGN_UP) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        signUpMessageLabel.setText("Connection is lost. Please sign up first.");
                    }
                });
            }
            else if (b.getResponse() == Response.FAILED_SIGNUP_INVALID_DATA && b.getCommand() == Commands.SIGN_UP) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        signUpMessageLabel.setText("Please enter valid email and password.");
                    }
                });
            }
        }
        catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
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
    public void setReader(ObjectInputStream reader) {
        this.reader = reader;
    }
    public ObjectInputStream getReader() {
        return reader;
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
