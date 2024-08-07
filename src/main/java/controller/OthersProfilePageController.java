package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.Database.DataBaseActions;
import org.common.*;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.common.Commands.GET_EDUCATIONS;
import static org.common.Commands.GET_SKILLS;

public class OthersProfilePageController implements Initializable {
    private String thisUsersEmail;
    private String otherUsersEmail;
    private String jwt;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private Socket socket;
    @FXML
    private HBox actionButtonsHBox;

    @FXML
    private ImageView backgroundPhotoImage;

    @FXML
    private Button connectButton;

    @FXML
    private Button contactInfoButton;

    @FXML
    private ListView<Education> educationListView;

    @FXML
    private Button exitButton;

    @FXML
    private Button followButton;

    @FXML
    private Label headlineLabel;

    @FXML
    private Button homeButton;

    @FXML
    private Label locationLabel;
    @FXML
    private Label requestSentLabel;

    @FXML
    private Button myProfileButton;
    @FXML
    private Button doneButton;
    @FXML
    private TextArea noteTextArea;

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView profilePhotoImage;

    @FXML
    private Button sendMessageButton;

    @FXML
    private ListView<Education> skillsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        noteTextArea.setDisable(true);
        doneButton.setDisable(true);
        requestSentLabel.setText("");
    }

    public void postInitialization() {
        DataBaseActions da = new DataBaseActions();

        File profileFile = new File(da.getProfilePicture(otherUsersEmail));
        Image prof = new Image(profileFile.toURI().toString());
        profilePhotoImage.setImage(prof);
        File backgroundFile = new File(da.getBackgroundPicture(otherUsersEmail));
        Image bg = new Image(backgroundFile.toURI().toString());
        backgroundPhotoImage.setImage(bg);
        Rectangle2D viewport = new Rectangle2D(0, 0, 800, 100);
        backgroundPhotoImage.setViewport(viewport);

        nameLabel.setText(da.getFirstname(otherUsersEmail) + " " + da.getLastname(otherUsersEmail));
        headlineLabel.setText(da.getHeadline(otherUsersEmail));
        locationLabel.setText((da.getCity(otherUsersEmail) == null?" ":da.getCity(otherUsersEmail) + ", ") + (da.getCountry(otherUsersEmail) == null?" ":da.getCountry(otherUsersEmail)));

        Bridge bridge = new Bridge(GET_EDUCATIONS, otherUsersEmail);
        SendMessage.send(bridge, writer);
        try {
            Bridge b = (Bridge) reader.readObject();
            if (b.getCommand() == GET_EDUCATIONS){
                ArrayList<Education> educationsArray = b.get();
                ObservableList<Education> educations = FXCollections.observableArrayList();
                educations.addAll(educationsArray);
                educationListView.setItems(educations);
                educationListView.setCellFactory(new Callback<ListView<Education>, ListCell<Education>>() {
                    @Override
                    public ListCell<Education> call(ListView<Education> param) {
                        return new EducationListCell(otherUsersEmail, reader, writer);
                    }
                });
                skillsListView.setItems(educations);
                skillsListView.setCellFactory(new Callback<ListView<Education>, ListCell<Education>>() {
                    @Override
                    public ListCell<Education> call(ListView<Education> param) {
                        return new SkillsListCell(otherUsersEmail, reader, writer);
                    }
                });
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (da.isUserFollowed(thisUsersEmail, otherUsersEmail))
            followButton.setDisable(true);
        if (da.doesConnectionExist(thisUsersEmail, otherUsersEmail))
            connectButton.setDisable(true);

    }

    @FXML
    void exitButtonPressed(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void homeButtonPressed(ActionEvent event) {
        LinkedInApplication.showHomePage(jwt);
    }

    @FXML
    void myProfileButtonPressed(ActionEvent event) {
        LinkedInApplication.showProfilePage();
    }

    @FXML
    void followButtonPressed(ActionEvent event) {
        DataBaseActions da = new DataBaseActions();
        da.followUsingEmail(thisUsersEmail, otherUsersEmail);
        followButton.setDisable(true);
    }

    @FXML
    void connectButtonPressed(ActionEvent event) {
        noteTextArea.setDisable(false);
        doneButton.setDisable(false);
    }

    @FXML
    void contactInfoButtonPressed(ActionEvent event) {
        LinkedInApplication.showContactInfoPage(otherUsersEmail);
    }

    @FXML
    void doneButtonPressed(ActionEvent event) {
        DataBaseActions da = new DataBaseActions();
        if (noteTextArea.getText().isEmpty()) {
            da.sendConnectionRequest(thisUsersEmail, otherUsersEmail, "");
        }
        else {
            da.sendConnectionRequest(thisUsersEmail, otherUsersEmail, noteTextArea.getText());
        }
        noteTextArea.setDisable(true);
        doneButton.setDisable(true);
        connectButton.setDisable(true);
        requestSentLabel.setText("Request was sent");
        requestSentLabel.setStyle("-fx-text-fill: #0b9b0b");
    }
    @FXML
    void sendMessageButtonPressed(ActionEvent event) {

    }

    public String getThisUsersEmail() {
        return thisUsersEmail;
    }

    public void setThisUsersEmail(String thisUsersEmail) {
        this.thisUsersEmail = thisUsersEmail;
    }

    public String getOtherUsersEmail() {
        return otherUsersEmail;
    }

    public void setOtherUsersEmail(String otherUsersEmail) {
        this.otherUsersEmail = otherUsersEmail;
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

    public ObjectOutputStream getWriter() {
        return writer;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
