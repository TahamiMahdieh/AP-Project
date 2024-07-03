package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class HashtagPageController implements Initializable {
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private String email;
    @FXML
    private Button exitButton;
    @FXML
    private Button homeButton;
    @FXML
    private Label hashtagWarningLabel;
    @FXML
    private TextField searchTextField;
    @FXML
    private ListView<PostObject> othersPostListView;
    @FXML
    private ListView<PostObject> myPostsListView;
    public void initialize (URL location, ResourceBundle resource) {
        // if user presses ENTER while writing in searchTextField, search will be executed
        searchTextField.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.ENTER) {
                searchHashtag();
            }
        });
        hashtagWarningLabel.setText("");
    }
    public void postInitialization (String hashtagWord) {
        String [] message = new String[] {email, hashtagWord};
        Bridge bridge1 = new Bridge(Commands.FIND_HASHTAG_MY_POSTS, message);
        SendMessage.send(bridge1, writer);
        try {
            Bridge b = (Bridge) reader.readObject();
            if (b.getCommand() == Commands.FIND_HASHTAG_MY_POSTS) {
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

        Bridge bridge2 = new Bridge(Commands.FIND_HASHTAG_OTHERS_POSTS, message);
        SendMessage.send(bridge2, writer);
        try {
            Bridge b = (Bridge) reader.readObject();
            if (b.getCommand() == Commands.FIND_HASHTAG_OTHERS_POSTS) {
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
    void homeButtonPressed(ActionEvent event) {
        LinkedInApplication.showHomePage(null);
    }

    public void searchHashtag () {
        String searchPhrase = searchTextField.getText();
        if (searchPhrase.startsWith("#")) {
            hashtagWarningLabel.setText("");
            searchTextField.getParent().requestFocus();
            LinkedInApplication.showHashtagPage(searchPhrase);
        }
        else {
            hashtagWarningLabel.setStyle("-fx-text-fill: #f12929");
            hashtagWarningLabel.setText("This is not a hashtag.Please try again");
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
}
