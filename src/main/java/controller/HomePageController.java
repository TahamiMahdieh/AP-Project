package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.Database.DataBaseActions;
import org.common.Bridge;
import org.common.Commands;
import org.common.SendMessage;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    private Socket socket;
    private ObjectInputStream reader;
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
    private Label noMatchFoundLabel;
    @FXML
    private ListView<String> myInformationListView;
    @FXML
    ListView<String> searchListView;
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
        // show the profile picture
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
        String searchPhrase = searchTextField.getText();
        searchTextField.getParent().requestFocus();
        Bridge bridge = new Bridge(Commands.SEARCH, searchPhrase);
        SendMessage.send(bridge, writer);
        startSearchTask();
    }
    public void startSearchTask(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                runSearchTask();
            }
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }
    public void runSearchTask () {
        try {
            Bridge b = (Bridge) reader.readObject();
            if (b.getCommand() == Commands.SEARCH) {
                ArrayList<String> names = b.get();
                if (names.isEmpty()){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            searchListView.getItems().clear();
                            noMatchFoundLabel.setText("No match was found");
                        }
                    });
                }
                else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            noMatchFoundLabel.setText("");
                            searchListView.getItems().clear();
                            ObservableList<String> searchResults = FXCollections.observableArrayList();
                            searchResults.addAll(names);
                            searchListView.setItems(searchResults);
                        }
                    });
                }
            }
        }
        catch (IOException | ClassNotFoundException e){
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public ObjectInputStream getReader() {
        return reader;
    }
    public void setReader(ObjectInputStream reader) {
        this.reader = reader;
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
