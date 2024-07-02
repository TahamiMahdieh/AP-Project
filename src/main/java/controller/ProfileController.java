package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.Database.DataBaseActions;
import org.common.Education;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private Button editPicturesButton;
    @FXML
    private Button addEducationButton;
    @FXML
    private ListView<String> educationListView;
    @FXML
    private ListView<String> skillsListView;
    @FXML
    private Label headlineLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Button contactInfoButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void postInitialization (){
        DataBaseActions da = new DataBaseActions();
        File profileFile = new File(da.getProfilePicture(email));
        Image prof = new Image(profileFile.toURI().toString());
        profilePhotoImage.setImage(prof);
        File backgroundFile = new File(da.getBackgroundPicture(email));
        Image bg = new Image(backgroundFile.toURI().toString());
        backgroundPhotoImage.setImage(bg);
        Rectangle2D viewport = new Rectangle2D(0, 0, 800, 100);
        backgroundPhotoImage.setViewport(viewport);

        nameLabel.setText(da.getFirstname(email) + " " + da.getLastname(email));
        headlineLabel.setText(da.getHeadline(email));
        locationLabel.setText(da.getAddress(email));

        List<Education> educations = da.getEducations(email);
        List<String> educationsString = new ArrayList<>();
        if (!(educations == null)) {
            for (Education e : educations) {
                educationsString.add(e.toString());
            }
            educationListView.setItems(FXCollections.observableList(educationsString));
        }

//        skillsListView.setItems(FXCollections.observableList());
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
        LinkedInApplication.showEditInfoPage();
    }
    @FXML
    void editPicturesButtonPressed(ActionEvent event) {
        LinkedInApplication.showEditPicturesPage();
    }
    @FXML
    void contactInfoButtonPressed(ActionEvent event) {

    }
    @FXML
    void addEducationButtonPressed (ActionEvent event) {
        LinkedInApplication.showAddEducationPage();
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
