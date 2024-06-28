package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.Database.DataBaseActions;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private String jwt;
    private String email;

    @FXML
    private Button exitButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button messagingButton;
    @FXML
    private Button myNetworkButton;
    @FXML
    private Button myProfileButton;
    @FXML
    private Button notificationsButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private ImageView backgroundPhotoImage;
    @FXML
    private ImageView profilePhotoImage;
    @FXML
    private Label nameLabel;
    @FXML
    private Button editInfoButton;
    @FXML
    private Label headlineLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Button contactInfoButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataBaseActions da = new DataBaseActions();

        String profilePhotoFilepath = da.getProfilePicture(email);
        Image profilePhoto = new Image(profilePhotoFilepath);
        profilePhotoImage.setImage(profilePhoto);

        String backgroundPhotoFilepath = da.getProfilePicture(email);
        Image backgroundPhoto = new Image(backgroundPhotoFilepath);
        backgroundPhotoImage.setImage(backgroundPhoto);

        nameLabel.setText(da.getFirstname(email) + da.getLastname(email));
        headlineLabel.setText(da.getHeadline(email));
        locationLabel.setText(da.getAddress(email));
    }

    @FXML
    void exitButtonPressed(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void homeButtonPressed(ActionEvent event) {

    }
    @FXML
    void messagingButtonPressed(ActionEvent event) {

    }
    @FXML
    void myNetworkButtonPressed(ActionEvent event) {

    }
    @FXML
    void myProfileButtonPressed(ActionEvent event) {

    }
    @FXML
    void notificationsButtonPressed(ActionEvent event) {

    }
    @FXML
    void editInfoButtonPressed(ActionEvent event) {

    }
    @FXML
    void contactInfoButtonPressed(ActionEvent event) {

    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
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

    public ObjectInputStream getReader() {
        return reader;
    }

    public void setReader(ObjectInputStream reader) {
        this.reader = reader;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
