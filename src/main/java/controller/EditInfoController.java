package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    private Button editContactInfoButton;
    @FXML
    private Label warningLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void postInitialization() {
        DataBaseActions da = new DataBaseActions();
        firstNameTextField.setText(da.getFirstname(email));
        lastNameTextField.setText(da.getLastname(email));
        additionalNameTextField.setText(da.getAdditionalName(email));
        headlineTextArea.setText(da.getHeadline(email));
    }

    @FXML
    void donePressed(ActionEvent event) {
        if (!warningChecker()) {
            DataBaseActions da = new DataBaseActions();
            da.setFirstname(email, firstNameTextField.getText().trim());
            da.setLastname(email, lastNameTextField.getText().trim());
            da.setAdditionalName(email, firstNameTextField.getText() == null ? null : firstNameTextField.getText().trim());
            da.setHeadline(email, headlineTextArea.getText().trim());

            LinkedInApplication.showProfilePage();
        }
    }
    @FXML
    void cancelPressed(ActionEvent event) {
        LinkedInApplication.showProfilePage();
    }

    @FXML
    void editContactInfoButtonPressed (ActionEvent event) {
        LinkedInApplication.showEditContactInfoPage();
    }

    public boolean warningChecker() {
        if (firstNameTextField.getText() == null || firstNameTextField.getText().trim().isEmpty())
            warningLabel.setText("First name cannot be empty.");
        else if (lastNameTextField.getText() == null || lastNameTextField.getText().trim().isEmpty())
            warningLabel.setText("Last name cannot be empty.");
        else if (headlineTextArea.getText() == null || headlineTextArea.getText().trim().isEmpty())
            warningLabel.setText("Headline cannot be empty.");
        else
            return false; // no warnings

        return true; // there are warning(s)
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

    public void firstNamePressed(ActionEvent actionEvent) {
    }

    public void lastNamePressed(ActionEvent actionEvent) {
    }

    public void additionalNamePressed(ActionEvent actionEvent) {
    }

    public void headlinePressed(MouseEvent mouseEvent) {
    }
}
