package org.common;

import controller.LinkedInApplication;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CommentsListCell extends ListCell<CommentObject> {
    private boolean playing = false;
    private VBox vBox;
    private Label label;
    private ImageView imageView;
    private MediaView mediaView;
    public CommentsListCell() {
        vBox = new VBox();
        vBox.setPrefWidth(370);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 20, 10 ,20));
        vBox.setStyle("-fx-border-color: #acacac");
    }
    @Override
    public void updateItem(CommentObject item, boolean empty){
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null); // don't display anything
        }
        else {
            vBox.getChildren().clear();

            if (item.getCommentText() != null){
                label = new Label(item.getCommentMakerName() + ": " + item.getCommentText());
                label.setWrapText(true);
                label.setMaxWidth(350);
                label.setFont(new Font("Comic Sans MS", 13));
                vBox.getChildren().add(label);
            }
            if (item.getImageDestination() != null){
                File imageFile = new File(item.getImageDestination());
                Image imageImage = new Image(imageFile.toURI().toString());
                imageView = new ImageView(imageImage);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(350);
                vBox.getChildren().add(imageView);
            }
            if (item.getVideoDestination() != null){
                File file = new File(item.getVideoDestination());
                String absolutePath = file.getAbsoluteFile().toURI().toString();
                Media media = new Media(absolutePath);
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaView = new MediaView(mediaPlayer);
                mediaView.setFitWidth(350);
                Button playButton = new Button("▶");
                playButton.setFont(new Font(12));
                playButton.setOnAction(e -> {
                    if (playing) {
                        mediaPlayer.pause();
                        playButton.setText("▶");
                    } else {
                        mediaPlayer.play();
                        playButton.setText("⏸");
                    }
                    playing = !playing;
                });
                vBox.getChildren().addAll(mediaView, playButton);
            }
            setGraphic(vBox);
        }
    }
}
