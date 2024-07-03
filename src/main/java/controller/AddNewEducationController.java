package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.Database.DataBaseActions;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddNewEducationController implements Initializable {
    private String email;
    private String jwt;
    private int skillCount = 0;
    private final int MAX_SKILLS = 5;
    private ArrayList<TextField> skillsTextFields = new ArrayList<>();

    @FXML
    private VBox mainVBox;
    @FXML
    private TextField schoolTextField;
    @FXML
    private TextField fieldOfStudyTextField;
    @FXML
    private DatePicker startDateDatePicker;
    @FXML
    private DatePicker endDateDatePicker;
    @FXML
    private TextField gradeTextField;
    @FXML
    private TextArea activitiesTextArea;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private VBox skillsVBox;
    @FXML
    private Button addSkillButton;
    @FXML
    private Label warningLabel;
    @FXML
    private CheckBox notifyNetworkCheckBox;
    @FXML
    private Button doneButton;
    @FXML
    private Button cancelButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void addSkillButtonPressed(ActionEvent event) {
        if (skillCount < MAX_SKILLS) {
            TextField newSkillTextField = new TextField();
            newSkillTextField.setPromptText("Enter skill");
            skillsVBox.getChildren().add(newSkillTextField);
            skillsTextFields.add(newSkillTextField);
            skillCount++;
        }
        if (skillCount == MAX_SKILLS) {
            addSkillButton.setDisable(true);
        }
    }

    @FXML
    void doneButtonPressed(ActionEvent event) {
        if (!warningCheck()) {
            DataBaseActions da = new DataBaseActions();
            ArrayList<String> skills = new ArrayList<>();

            for (int i = 0; i < skills.size(); i++) {
                if (!skillsTextFields.get(i).getText().trim().isEmpty()) {
                    skills.add(skillsTextFields.get(i).getText());
                }
            }

            String skillsString = String.join(",", skills);

            da.addEducation(email, schoolTextField.getText(), fieldOfStudyTextField.getText(), Date.valueOf(startDateDatePicker.getValue()), Date.valueOf(endDateDatePicker.getValue()), Integer.parseInt(gradeTextField.getText()), activitiesTextArea.getText(), descriptionTextArea.getText(), skillsString,notifyNetworkCheckBox.isSelected());
            LinkedInApplication.showProfilePage();
        }
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        LinkedInApplication.showProfilePage();
    }
    public boolean warningCheck() {
        if (schoolTextField.getText().trim().isEmpty()) {
            warningLabel.setText("School cannot be blank.");
            return true; // warning exists
        }
        if (!gradeTextField.getText().trim().isEmpty()) {
            try {
                Integer.parseInt(gradeTextField.getText());
            } catch (NumberFormatException e) {
                warningLabel.setText("Grade must be a number.");
                return true; // warning exists
            }
        }
        return false; // no warnings
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
