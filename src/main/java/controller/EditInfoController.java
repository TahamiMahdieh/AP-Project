package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.Database.DataBaseActions;

import java.net.URL;
import java.util.ResourceBundle;

public class EditInfoController implements Initializable {
    private String email;
    private String jwt;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField additionalNameTextField;
    @FXML
    private TextArea headlineTextArea;
    @FXML
    private Button doneButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label warningLabel;

    private String newFirstName = null;
    private String newLastName = null;
    private String newAdditionalName = null;
    private String newHeadline = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataBaseActions da = new DataBaseActions();
        firstNameTextField.setText(da.getFirstname(email));
        lastNameTextField.setText(da.getLastname(email));
        additionalNameTextField.setText("" + da.getAdditionalName(email));
        headlineTextArea.setText(da.getHeadline(email));
    }
    @FXML
    void firstNamePressed(ActionEvent event) {
        newFirstName = firstNameTextField.getText();
        if (newFirstName.trim().equals("")) {
            warningLabel.setText("First name cannot be empty.");
        }
    }
    @FXML
    void lastNamePressed(ActionEvent event) {
        newLastName = lastNameTextField.getText();
        if (newLastName.trim().equals("")) {
            warningLabel.setText("Last name cannot be empty.");
        }
    }
    @FXML
    void additionalNamePressed(ActionEvent event) {
        newAdditionalName = additionalNameTextField.getText();
    }
    @FXML
    void headlinePressed(ActionEvent event) {
        newHeadline = headlineTextArea.getText();
        if (newHeadline.trim().equals("")) {
            warningLabel.setText("Headline cannot be empty.");
        }
    }
    @FXML
    void donePressed(ActionEvent event) {
        DataBaseActions da = new DataBaseActions();
        if (newFirstName != null && !newFirstName.trim().equals(""))
            da.setFirstname(email, newFirstName);
        if (newLastName != null && !newLastName.trim().equals(""))
            da.setLastname(email, newLastName);
        if (newAdditionalName != null)
            da.setAdditionalName(email, newAdditionalName);
        if (newHeadline != null && !newHeadline.trim().equals(""))
            da.setHeadline(email, newHeadline);

    }
    @FXML
    void cancelPressed(ActionEvent event) {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
