package controller;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.Database.DataBaseActions;

import java.net.URL;
import java.sql.Date;
import java.util.*;

public class EditContactInfoController implements Initializable {

    private String email;
    private String jwt;
    private final String[] PHONE_TYPE_CHOICES  = new String[]{"Home", "Work", "Mobile"};
    private final String[] BIRTH_DATE_PRIVACY_CHOICES  = new String[]{"Only you", "Your connections", "Your network", "All LinkedIn members"};
    private final String[] SERVICE_CHOICES  = new String[]{"Skype", "ICQ", "Google Hangouts", "QQ", "WeChat"};

    @FXML
    private Hyperlink profileLink; // has to be implemented
    @FXML
    private Label emailLabel;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private ChoiceBox<String> phoneTypeChoiceBox;
    @FXML
    private TextArea addressTextArea;
    @FXML
    private DatePicker birthDateDatePicker;
    @FXML
    private ChoiceBox<String> birthDatePrivacyChoiceBox;
    @FXML
    private Button addMessagingButton;
    @FXML
    private VBox messagingVBox;
    @FXML
    private Label warningLabel;
    @FXML
    private Button doneButton;
    @FXML
    private Button cancelButton;

    private int messagingOptionsCount;
    private final int MAX_MESSAGING_OPTIONS = 1;
    private ArrayList<VBox> messagingOptionsVBoxes = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void postInitialization() {
        DataBaseActions da = new DataBaseActions();

        emailLabel.setText(email);
        phoneNumberTextField.setText(da.getPhoneNumber(email));
        phoneTypeChoiceBox.setValue("Please choose");
        phoneTypeChoiceBox.setItems(FXCollections.observableList(List.of(PHONE_TYPE_CHOICES)));
        addressTextArea.setText(da.getAddress(email));
        if (da.getBirthDate(email) != null)
            birthDateDatePicker.setValue(da.getBirthDate(email).toLocalDate());
        birthDatePrivacyChoiceBox.setValue("Please choose");
        birthDatePrivacyChoiceBox.setItems(FXCollections.observableList(List.of(BIRTH_DATE_PRIVACY_CHOICES)));
    }

    @FXML
    void addMessagingButtonPressed(ActionEvent event) {
        if (messagingOptionsCount < MAX_MESSAGING_OPTIONS) {
            VBox newOptionVBox = new VBox();
            TextField optionTextField = new TextField();
            ChoiceBox<String> serviceChoiceBox = new ChoiceBox<String>(FXCollections.observableList(List.of(SERVICE_CHOICES)));
            optionTextField.setPromptText("Enter username");
            newOptionVBox.getChildren().add(optionTextField);
            newOptionVBox.getChildren().add(serviceChoiceBox);
            messagingOptionsVBoxes.add(newOptionVBox);
            messagingOptionsCount++;
        }
        if (messagingOptionsCount == MAX_MESSAGING_OPTIONS) {
            addMessagingButton.setDisable(true);
        }
    }

    @FXML
    void doneButtonPressed(ActionEvent event) {
        if (!warningCheck()) {
            DataBaseActions da = new DataBaseActions();
            da.setPhoneNumber(email, phoneNumberTextField.getText().trim());
            da.setPhoneType(email, phoneTypeChoiceBox.getValue());
            da.setAddress(email, addressTextArea.getText());
            da.setBirthDate(email, Date.valueOf(birthDateDatePicker.getValue()));
            da.setBirthDatePrivacy(email, birthDatePrivacyChoiceBox.getValue());
            da.setInstantMessaging(email, ((TextField) messagingOptionsVBoxes.get(0).getChildren().get(0)).getText());

            LinkedInApplication.showEditInfoPage();
        }
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        LinkedInApplication.showEditInfoPage();
    }

    public boolean warningCheck() {
        try {
            Integer.parseInt(phoneNumberTextField.getText());
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
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
