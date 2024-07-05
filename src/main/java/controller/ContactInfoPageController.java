package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.Database.DataBaseActions;
import org.common.Bridge;
import org.common.Commands;
import org.common.SendMessage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactInfoPageController implements Initializable {
    private String email;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private Socket socket;

    @FXML
    private Label addressLabel;

    @FXML
    private Label birthDateLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label messagingLabel;

    @FXML
    private Button okButton;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label phoneTypeLabel;

    @FXML
    private Label profileURLLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void postInitialization() {
        Bridge bridge = new Bridge(Commands.GET_CONTACT_INFO, email);
        SendMessage.send(bridge, writer);
        try {
            Bridge b = (Bridge)
        }
    }

    @FXML
    void okButtonPressed(ActionEvent event) {

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
