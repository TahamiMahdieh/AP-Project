package org.common;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import org.Database.DataBaseActions;


import static javafx.scene.paint.Color.WHITE;

public class FollowingTextCell extends ListCell<String> {
    private GridPane gridPane;
    private Label userInfo;
    private Button deleteButton;

    public FollowingTextCell(String followerEmail) {
        gridPane = new GridPane();
        gridPane.setPrefSize(708, 25);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(400);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(400);
        col2.setHalignment(HPos.RIGHT);

        gridPane.getColumnConstraints().addAll(col1, col2);

        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(25);
        gridPane.getRowConstraints().add(row1);

        // Children
        deleteButton = new Button("❌ Delete");
        deleteButton.setPrefSize(85, 26);
        deleteButton.setStyle("-fx-background-color: #0a66cb;");
        deleteButton.setTextFill(WHITE);
        deleteButton.setOnAction(event -> {
            DataBaseActions d = new DataBaseActions();
            String[] split = userInfo.getText().split("->");
            String followeeEmail = split[1].trim();
            d.unfollowUsingEmail(followerEmail, followeeEmail);
            String item = getItem();
            getListView().getItems().remove(item);
        });
        GridPane.setColumnIndex(deleteButton, 1);

        userInfo = new Label();
        userInfo.setPrefSize(322, 30);
        userInfo.setFont(new Font(13));
        gridPane.getChildren().addAll(deleteButton, userInfo);

        // Padding
        gridPane.setPadding(new Insets(0, 20, 0, 20));
    }

    @Override
    protected void updateItem (String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null); // don't display anything
        }
        else {
            userInfo.setText(item);
            setGraphic(gridPane);
        }
    }
}
