package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.Database.DataBaseActions;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class OthersProfilePageController implements Initializable {
    private String thisUsersEmail;
    private String otherUsersEmail;
    private String jwt;
    @FXML
    private Button homeButton;
    @FXML
    private Button myProfileButton;
    @FXML
    private Button exitButton;
    @FXML
    private ImageView profilePhotoImage;
    @FXML
    private ImageView backgroundPhotoImage;
    @FXML
    private Label nameLabel;
    @FXML
    private Label headlineLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Button contactInfoButton;
    @FXML
    private HBox actionButtonsHBox;
    @FXML
    private TabPane postsAndCommentsTabPane;
    @FXML
    private ListView<String> postsListView;
    @FXML
    private ListView<String> commentsListView;
    @FXML
    private ListView<String> educationListView;
    @FXML
    private ListView<String> skillsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    }

    @FXML
    void exitButtonPressed(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
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

    public void setEmail(String thisUserEmail) {
        this.thisUsersEmail = thisUserEmail;
    }
}
