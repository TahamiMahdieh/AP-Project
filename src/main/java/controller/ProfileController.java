package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.common.Commands.GET_EDUCATIONS;
import static org.common.Commands.GET_SKILLS;

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
    private ListView<Education> educationListView;
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
        locationLabel.setText(da.getCity(email) + ", " + da.getCountry(email));

        Bridge bridge = new Bridge(GET_EDUCATIONS, email);
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
                        return new EducationListCell(email, reader, writer);
                    }
                });
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


//        Bridge bridge1 = new Bridge(GET_SKILLS, email);
//        SendMessage.send(bridge1, writer);
//        try {
//            Bridge b = (Bridge) reader.readObject();
//            if (b.getCommand() == GET_SKILLS) {
//                ArrayList<String> skillsArrayList = b.get();
//                ObservableList<String> skills = FXCollections.observableArrayList();
//                skills.addAll(skillsArrayList);
//                skillsListView.setItems(skills);
//            }
//        }
//        catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void exitButtonPressed(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void homeButtonPressed(ActionEvent event) {
        LinkedInApplication.showHomePage(null);
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
        LinkedInApplication.showContactInfoPage(email);
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
