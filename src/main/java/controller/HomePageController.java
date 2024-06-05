package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    private Socket socket;
    private ObjectOutputStream writer;
    private String jwt;
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
    @Override
    public void initialize (URL location, ResourceBundle resource) {
        // if user presses ENTER while writing in searchTextField, search will be executed
        searchTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    search();
                    break;
                default:
                    break;
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
    public void search (){
        String searchedQuery = searchTextField.getText();
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
}
