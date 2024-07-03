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

import org.common.*;


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
    @FXML
    private ListView<PostObject> othersPostListView;
    @FXML
    private ListView<PostObject> myPostsListView;
    public void initialize (URL location, ResourceBundle resource) {
        // if user presses ENTER while writing in searchTextField, search will be executed
        searchTextField.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.ENTER) {
                search();
            }
        });
    }
    public void postInitialization () {
        // define and manifest the information ListView
        Bridge bridge = new Bridge(Commands.HOMEPAGE_INFORMATION_LISTVIEW, email);
        SendMessage.send(bridge, writer);
        try {
            Bridge b = (Bridge) reader.readObject();
            if (b.getCommand() == Commands.HOMEPAGE_INFORMATION_LISTVIEW) {
                ArrayList<String> info = b.get();
                if (info.get(2) != null) {
                    ObservableList<String> users = FXCollections.observableArrayList(
                            info.get(0) + " " + info.get(1),
                            info.get(2)
                    );
                    // Set items to the ListView
                    myInformationListView.setItems(users);
                } else {
                    ObservableList<String> users = FXCollections.observableArrayList(
                            info.get(0) + " " + info.get(1)
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
                File profileFile = new File(info.get(3));
                Image prof = new Image(profileFile.toURI().toString());
                profilePicture.setImage(prof);
            }
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        Bridge bridge1 = new Bridge(Commands.SHOW_MY_POSTS, email);
        SendMessage.send(bridge1, writer);
        try {
            Bridge b = (Bridge) reader.readObject();
            if (b.getCommand() == Commands.SHOW_MY_POSTS) {
                ArrayList<PostObject> posts = b.get();
                ObservableList<PostObject> postsObservable = FXCollections.observableArrayList();
                postsObservable.addAll(posts);
                myPostsListView.setItems(postsObservable);
                myPostsListView.setCellFactory(new Callback<ListView<PostObject>, ListCell<PostObject>>() {
                    @Override
                    public ListCell<PostObject> call(ListView<PostObject> param) {
                        return new PostsTextCell(email, reader, writer);
                    }
                });
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        Bridge bridge2 = new Bridge(Commands.SHOW_OTHERS_POSTS, email);
        SendMessage.send(bridge2, writer);
        try {
            Bridge b = (Bridge) reader.readObject();
            if (b.getCommand() == Commands.SHOW_OTHERS_POSTS) {
                ArrayList<PostObject> posts = b.get();
                ObservableList<PostObject> postsObservable = FXCollections.observableArrayList();
                postsObservable.addAll(posts);
                othersPostListView.setItems(postsObservable);
                othersPostListView.setCellFactory(new Callback<ListView<PostObject>, ListCell<PostObject>>() {
                    @Override
                    public ListCell<PostObject> call(ListView<PostObject> param) {
                        return new OthersPostsTextCell(email, reader, writer);
                    }
                });
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void exitButtonPressed(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void messagingButtonPressed(ActionEvent event) {

    }
    @FXML
    void myNetworkButtonPressed(ActionEvent event) {
        LinkedInApplication.showMyNetworkPage();
    }
    @FXML
    void myProfileButtonPressed(ActionEvent event) {
        LinkedInApplication.showProfilePage();
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
