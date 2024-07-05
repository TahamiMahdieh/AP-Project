package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.common.Bridge;
import org.common.Commands;
import org.common.SendMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactInfoPageController implements Initializable {
    private String othersEmail;
    private String thisUsersEmail;
    private boolean thisUser = false;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void postInitialization() {
        Bridge bridge = new Bridge(Commands.GET_CONTACT_INFO, othersEmail);
        SendMessage.send(bridge, writer);
        try {
            Bridge b = (Bridge) reader.readObject();
            if (b.getCommand() == Commands.GET_CONTACT_INFO) {
                String[] contactInfo = b.get();
                emailLabel.setText(contactInfo[1]);
                phoneNumberLabel.setText(contactInfo[2]);
                phoneTypeLabel.setText(contactInfo[3]);
                addressLabel.setText(contactInfo[4]);
                messagingLabel.setText(contactInfo[7]);

                if (thisUser) {
                    birthDateLabel.setText(contactInfo[5]);
                }
                else if (contactInfo[6].equals("Only you")) {
                    birthDateLabel.setText("");
                }
                else if (contactInfo[6].equals("All LinkedIn members")) {
                    birthDateLabel.setText(contactInfo[5]);
                }
                else {
                    bridge = new Bridge(Commands.ARE_USERS_CONNECTED, new String[]{othersEmail, thisUsersEmail});
                    SendMessage.send(bridge, writer);
                    try {
                        b = (Bridge) reader.readObject();
                        if (b.getCommand() == Commands.ARE_USERS_CONNECTED) {
                            boolean areUsersConnected = b.get();
                            if (areUsersConnected)
                                birthDateLabel.setText(contactInfo[5]);
                            else
                                birthDateLabel.setText("");
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }


                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void okButtonPressed(ActionEvent event) {
        if (thisUser) {
            LinkedInApplication.showProfilePage();
        } else {
            LinkedInApplication.showOthersProfilePage(othersEmail);
        }
    }

    public String getOthersEmail() {
        return othersEmail;
    }

    public void setOthersEmail(String othersEmail) {
        this.othersEmail = othersEmail;
    }

    public String getThisUsersEmail() {
        return thisUsersEmail;
    }

    public void setThisUsersEmail(String thisUsersEmail) {
        this.thisUsersEmail = thisUsersEmail;
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

    public boolean isThisUser() {
        return thisUser;
    }

    public void setThisUser(boolean thisUser) {
        this.thisUser = thisUser;
    }
}
