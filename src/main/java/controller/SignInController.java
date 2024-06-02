package controller;

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
import org.common.Bridge;
import org.common.Commands;
import org.common.SendMessage;
import org.common.User;

import java.io.File;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;
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
                Bridge bridge = new Bridge(Commands.SIGN_IN, u, null);
                loginMessageLabel.setText("Loading ...");
                SendMessage.send(bridge, writer);
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
}