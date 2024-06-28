package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.Database.DataBaseActions;

import java.io.File;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    private Socket socket;
    private ObjectOutputStream writer;
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
    private ListView<String> myInformationListView;
    @FXML
    private ImageView profilePicture;
    @FXML
    private TextField postTextField;
    public void initialize (URL location, ResourceBundle resource) {
        // if user presses ENTER while writing in searchTextField, search will be executed
        searchTextField.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.ENTER) {
                search();
            }
        });
    }
    public void postInitialization (){
        // define and manifest the information ListView
        DataBaseActions da = new DataBaseActions();
        if (da.getHeadline(email) != null) {
            ObservableList<String> users = FXCollections.observableArrayList(
                    da.getFirstname(email) + " " + da.getLastname(email),
                    da.getHeadline(email)
            );
            // Set items to the ListView
            myInformationListView.setItems(users);
        }
        else {
            ObservableList<String> users = FXCollections.observableArrayList(
                    da.getFirstname(email) + " " + da.getLastname(email)
            );
            // Set items to the ListView
            myInformationListView.setItems(users);
        }
        myInformationListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new CustomListCell();
            }
        });
        // initializing the profile picture
        File profileFile = new File(da.getProfilePicture(email));
        Image prof = new Image(profileFile.toURI().toString());
        profilePicture.setImage(prof);
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
    public void post (){
        postTextField.getParent().requestFocus();
        LinkedInApplication.showPostingPage();
    }
    public void search () {
        searchTextField.getParent().requestFocus();
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    class CustomListCell extends ListCell<String> {
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item);
                setStyle("-fx-font-size: 13px; -fx-alignment: LEFT;");
            }
        }
    }
}
