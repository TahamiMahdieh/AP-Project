package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import org.Database.DataBaseActions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class EditPicturesController implements Initializable {
    private String email;
    private String jwt;
    @FXML
    private Pane profilePhotoPane;
    @FXML
    private Pane backgroundPhotoPane;
    @FXML
    private Label profilePhotoWarningLabel;
    @FXML
    private Label backgroundPhotoWarningLabel;
    @FXML
    private Button doneButton;
    @FXML
    private Button cancelButton;
    private File newProfilePhoto;
    private File newBackgroundPhoto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profilePhotoPane.setOnDragOver(event -> handleDragOver(event));
        profilePhotoPane.setOnDragDropped(event -> handleProfilePhotoDragDropped(event));
        backgroundPhotoPane.setOnDragOver(event -> handleDragOver(event));
        backgroundPhotoPane.setOnDragDropped(event -> handleBackgroundPhotoDragDropped(event));
    }

    public void postInitialization() {

    }

    @FXML
    private void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    private void handleProfilePhotoDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;

        if (dragboard.hasFiles()) {
            success = true;
            File file = dragboard.getFiles().get(0);
            if (file != null && file.getName().matches(".*\\.(png|jpg|jpeg|gif)$")) {
                newProfilePhoto = file;
                profilePhotoWarningLabel.setStyle("-fx-text-fill: #1a9a00");
                profilePhotoWarningLabel.setText("Photo received.");
            }
            else {
                profilePhotoWarningLabel.setStyle("-fx-text-fill: #ff3d00");
                profilePhotoWarningLabel.setText("That's not a photo!");
            }
        }
        else {
            profilePhotoWarningLabel.setStyle("-fx-text-fill: #ff3d00");
            profilePhotoWarningLabel.setText("That's not a file!");
        }

        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void handleBackgroundPhotoDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;

        if (dragboard.hasFiles()) {
            success = true;
            File file = dragboard.getFiles().get(0);
            if (file != null && file.getName().matches(".*\\.(png|jpg|jpeg|gif)$")) {
                newBackgroundPhoto = file;
                backgroundPhotoWarningLabel.setStyle("-fx-text-fill: #1a9a00");
                backgroundPhotoWarningLabel.setText("Photo received.");
            }
            else {
                backgroundPhotoWarningLabel.setStyle("-fx-text-fill: #ff3d00");
                backgroundPhotoWarningLabel.setText("That's not a photo!");
            }
        }
        else {
            backgroundPhotoWarningLabel.setStyle("-fx-text-fill: #ff3d00");
            backgroundPhotoWarningLabel.setText("That's not a file!");
        }

        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    void doneButtonPressed(ActionEvent event) {
        if (newProfilePhoto != null) {
            try {
                // Save file to specified directory
                String fileName = newProfilePhoto.getName();
                Path destination = Path.of("pictures/", fileName);
                Files.copy(newProfilePhoto.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

                // Update the profile picture path in the database
                DataBaseActions da = new DataBaseActions();
                da.setProfilePicture(email, destination);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (newBackgroundPhoto != null) {
            try {
                // Save file to specified directory
                String fileName = newBackgroundPhoto.getName();
                Path destination = Path.of("pictures/", fileName);
                Files.copy(newBackgroundPhoto.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

                // Update the profile picture path in the database
                DataBaseActions da = new DataBaseActions();
                da.setBackgroundPicture(email, destination);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LinkedInApplication.showProfilePage();
    }

    @FXML
    void cancelButtonPressed(ActionEvent event) {
        LinkedInApplication.showProfilePage();
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
