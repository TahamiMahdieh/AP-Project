package org.common;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import org.Database.DataBaseActions;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PostsTextCell extends ListCell<PostObject> {
    private boolean playing = false;
    private VBox vBox;
    private Label label;
    private ImageView imageView;
    private MediaView mediaView;
    private Button deleteButton;
    private Button likeButton;
    private Button showLikeListButton;
    private Button commentButton;
    private Button showCommentListButton;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private String email;
    private boolean isLiked;
    public PostsTextCell(String email, ObjectInputStream reader, ObjectOutputStream writer) {
        this.reader = reader;
        this.writer = writer;
        this.email = email;
        vBox = new VBox();
        vBox.setPrefWidth(340);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 20, 10 ,20));
        vBox.setStyle("-fx-border-color: #acacac");
        deleteButton = new Button("Delete");
        commentButton = new Button("Comment");
        showCommentListButton = new Button("See comments");
        showLikeListButton = new Button("See likes");
        likeButton = new Button("like");

    }
    @Override
    public void updateItem(PostObject item, boolean empty){
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null); // don't display anything
        }
        else {
            item.setPostVisitor(email);
            vBox.getChildren().clear();

            if (item.getPostText() != null){
                label = new Label(item.getPostText());
                label.setWrapText(true);
                label.setMaxWidth(340);
                label.setFont(new Font("Comic Sans MS", 13));
                vBox.getChildren().add(label);
            }
            if (item.getImageDestination() != null){
                File imageFile = new File(item.getImageDestination());
                Image imageImage = new Image(imageFile.toURI().toString());
                imageView = new ImageView(imageImage);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(340);
                vBox.getChildren().add(imageView);
            }
            if (item.getVideoDestination() != null){
                File file = new File(item.getVideoDestination());
                String absolutePath = file.getAbsoluteFile().toURI().toString();
                Media media = new Media(absolutePath);
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaView = new MediaView(mediaPlayer);
                mediaView.setFitWidth(340);
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

            // delete button
            deleteButton.setOnAction(event -> {
                Bridge b = new Bridge(Commands.DELETE_MY_POST, item);
                SendMessage.send(b, writer);
                getListView().getItems().remove(item);
            });

            //like button
            Bridge b = new Bridge(Commands.IS_ALREADY_LIKED, item);
            SendMessage.send(b, writer);
            try {
                Bridge bridge = (Bridge) reader.readObject();
                boolean isPostLiked = bridge.get();
                if (isPostLiked){
                    likeButton.setStyle("-fx-background-color: #ff3333");
                    isLiked = true;
                }
                else {
                    isLiked = false;
                }
            }
            catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            likeButton.setOnAction(event ->{
                if (isLiked){
                    isLiked = false;
                    likeButton.setStyle("-fx-background-color: #ecece7");
                    Bridge bridge = new Bridge(Commands.DELETE_LIKE, item);
                    SendMessage.send(bridge, writer);
                }
                else {
                    isLiked = true;
                    likeButton.setStyle("-fx-background-color: #ff3333");
                    Bridge bridge = new Bridge(Commands.ADD_LIKE, item);
                    SendMessage.send(bridge, writer);
                }
            });
            HBox hBox = new HBox();
            hBox.setPrefWidth(340);
            hBox.setSpacing(5);
            hBox.getChildren().addAll(showCommentListButton, commentButton, showLikeListButton, likeButton, deleteButton);
            vBox.getChildren().add(hBox);
            setGraphic(vBox);
        }
    }
}
