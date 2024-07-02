package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.common.Bridge;
import org.common.FollowingTextCell;
import org.common.LikesTextCell;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LikeListPageController implements Initializable {
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private String email;
    @FXML
    private Button exitButton;
    @FXML
    private Button homeButton;
    @FXML
    private ListView<String> likesListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void postInitialize(Bridge b){
        ArrayList<String> likesArray = b.get();
        ObservableList<String> likes = FXCollections.observableArrayList();
        likes.addAll(likesArray);
        likesListView.setItems(likes);
        // set custom ListView cell factory
        likesListView.setCellFactory(new Callback<ListView<String>, ListCell<String >>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                        return new LikesTextCell();
                    }
        });
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

    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
