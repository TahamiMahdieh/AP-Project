package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;
    @FXML
    private Button cancelButton;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

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

    }

    @FXML
    void signUpButtonPressed(ActionEvent event) {

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
