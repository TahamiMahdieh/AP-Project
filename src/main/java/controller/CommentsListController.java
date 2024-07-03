package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.common.Bridge;
import org.common.CommentObject;
import org.common.CommentsListCell;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CommentsListController implements Initializable {
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private Socket socket;
    private String myEmail;
    private String postId;
    @FXML
    private ListView<CommentObject> commentsListView;
    @FXML
    private Button exitButton;
    @FXML
    private Button homeButton;

    @FXML
    void exitButtonPressed(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void homeButtonPressed(ActionEvent event) {
        LinkedInApplication.showHomePage(null);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void postInitialize(Bridge b){
        ArrayList<CommentObject> commentArray = b.get();
        ObservableList<CommentObject> comments = FXCollections.observableArrayList();
        comments.addAll(commentArray);
        commentsListView.setItems(comments);
        // set custom ListView cell factory
        commentsListView.setCellFactory(new Callback<ListView<CommentObject>, ListCell<CommentObject>>() {
            @Override
            public ListCell<CommentObject> call(ListView<CommentObject> param) {
                return new CommentsListCell();
            }
        });
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
    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public String getEmail() {
        return myEmail;
    }
    public void setEmail(String myEmail) {
        this.myEmail = myEmail;
    }
    public String getPostId() {
        return postId;
    }
    public void setPostId(String postId) {
        this.postId = postId;
    }
}
