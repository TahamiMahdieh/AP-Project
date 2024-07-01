package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.common.Bridge;
import org.common.Commands;
import org.common.PostObject;
import org.common.SendMessage;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class PostingPageController implements Initializable {
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private String email;
    private File image;
    private File video;

    @FXML
    private Button cancelButton;
    @FXML
    private Pane imagePane;
    @FXML
    private Label pictureWarningLabel;
    @FXML
    private Button saveButton;
    @FXML
    private TextArea textTextField;
    @FXML
    private Pane videoPane;
    @FXML
    private Label videoWarningLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagePane.setOnDragOver(event -> dragDetected(event));
        imagePane.setOnDragDropped(event -> imageDragAndDropped(event));
        videoPane.setOnDragOver(event -> dragDetected(event));
        videoPane.setOnDragDropped(event -> videoDragAndDropped(event));
    }
    @FXML
    void cancelButtonPressed(ActionEvent event) {
        LinkedInApplication.showHomePage(email);
    }
    @FXML
    void dragDetected(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }
    @FXML
    private void imageDragAndDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if (dragboard.hasFiles()) {
            success = true;
            File file = dragboard.getFiles().getFirst();
            if (file != null && file.getName().matches(".*\\.(png|jpg|jpeg|gif)$")) {
                image = file;
                pictureWarningLabel.setStyle("-fx-text-fill: #1a9a00");
                pictureWarningLabel.setText("Photo received.");
            }
            else {
                pictureWarningLabel.setStyle("-fx-text-fill: #ff3d00");
                pictureWarningLabel.setText("That's not a photo!");
            }
        }
        else {
            pictureWarningLabel.setStyle("-fx-text-fill: #ff3d00");
            pictureWarningLabel.setText("That's not a file!");
        }
        event.setDropCompleted(success);
        event.consume();
    }
    @FXML
    private void videoDragAndDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;

        if (dragboard.hasFiles()) {
            success = true;
            File file = dragboard.getFiles().get(0);
            if (file != null && file.getName().matches(".*\\.(mp4|avi|mov|mkv)$")) {
                video = file;
                videoWarningLabel.setStyle("-fx-text-fill: #1a9a00");
                videoWarningLabel.setText("Video received.");
            }
            else {
                videoWarningLabel.setStyle("-fx-text-fill: #ff3d00");
                videoWarningLabel.setText("That's not a video!");
            }
        }
        else {
            videoWarningLabel.setStyle("-fx-text-fill: #ff3d00");
            videoWarningLabel.setText("That's not a file!");
        }

        event.setDropCompleted(success);
        event.consume();
    }
    @FXML
    private void saveButtonpressed(ActionEvent event) {
        if (textTextField.getText() != null || video != null || image != null) {
            PostObject postObject = new PostObject();
            postObject.setUserEmail(email);
            if (textTextField.getText() != null) {
                postObject.setPostText(textTextField.getText());
            }
            if (image != null) {
                postObject.setImageFile(image);
            }
            if (video != null) {
                postObject.setVideoFile(video);
            }
            Bridge b = new Bridge(Commands.POST_THIS, postObject);
            SendMessage.send(b, writer);
        }
        LinkedInApplication.showHomePage(email);
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

}
