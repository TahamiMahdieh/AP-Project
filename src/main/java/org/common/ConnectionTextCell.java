package org.common;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.Database.DataBaseActions;

import static javafx.geometry.Pos.CENTER_RIGHT;
import static javafx.scene.paint.Color.WHITE;

public class ConnectionTextCell extends ListCell<GetConnectionReturn> {
    private GridPane gridPane;
    private Label userInfoLabel;
    private Button disconnectButton;
    private Button acceptButton;
    private Button rejectButton;
    private final String email;

    public ConnectionTextCell(String email) {
        this.email = email;
        gridPane = new GridPane();
        gridPane.setPrefWidth(708);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(300);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(500);
        col2.setHalignment(HPos.RIGHT);
        gridPane.getColumnConstraints().addAll(col1, col2);

        RowConstraints row1 = new RowConstraints();
        gridPane.getRowConstraints().add(row1);

        userInfoLabel = new Label();
        userInfoLabel.setPrefSize(322, 30);
        userInfoLabel.setFont(new Font(13));

        gridPane.setPadding(new Insets(0, 20, 0, 20));
    }

    @Override
    protected void updateItem(GetConnectionReturn item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(null);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            DataBaseActions d = new DataBaseActions();
            gridPane.getChildren().clear();
            userInfoLabel.setText(item.getFirstname() + " " + item.getLastname() + " -> " + item.getEmail());

            switch (item.getStatus()) {
                case "accepted" -> {
                    disconnectButton = new Button("Disconnect");
                    disconnectButton.setPrefSize(80, 26);
                    disconnectButton.setStyle("-fx-background-color: #0a66cb;-fx-padding: 5px 10px;");
                    disconnectButton.setTextFill(WHITE);
                    disconnectButton.setOnAction(event -> {
                        d.disconnect(email, item.getEmail());
                        getListView().getItems().remove(item);
                    });
                    HBox buttonsBox = new HBox();
                    buttonsBox.setSpacing(10);
                    buttonsBox.setAlignment(CENTER_RIGHT);
                    buttonsBox.getChildren().addAll(disconnectButton);
                    GridPane.setColumnIndex(buttonsBox, 1);
                    gridPane.getChildren().addAll(buttonsBox, userInfoLabel);
                }
                case "pending" -> {
                    if (item.getMyRole().equals("sender")) {
                        disconnectButton = new Button("Disconnect");
                        disconnectButton.setPrefSize(80, 26);
                        disconnectButton.setStyle("-fx-background-color: #0a66cb; -fx-padding: 5px 10px;");
                        disconnectButton.setTextFill(WHITE);
                        disconnectButton.setOnAction(event -> {
                            d.deleteRequest(email, item.getEmail());
                            getListView().getItems().remove(item);
                        });
                        Label l = new Label("note: " + getItem().getNote());
                        l.setWrapText(true);
                        l.setMaxWidth(120);
                        HBox buttonsBox = new HBox();
                        buttonsBox.setSpacing(10);
                        buttonsBox.setAlignment(CENTER_RIGHT);
                        buttonsBox.getChildren().addAll(l, disconnectButton);
                        GridPane.setColumnIndex(buttonsBox, 1);
                        gridPane.getChildren().addAll(buttonsBox, userInfoLabel);
                    } else {
                        rejectButton = new Button("Reject");
                        rejectButton.setPrefSize(70, 26);
                        rejectButton.setStyle("-fx-background-color: #0a66cb; -fx-padding: 5px 10px;");
                        rejectButton.setTextFill(WHITE);
                        rejectButton.setOnAction(event -> {
                            d.rejectConnectionRequest(item.getEmail(), email);
                            getListView().getItems().remove(item);
                        });
                        Label l = new Label("note: " + getItem().getNote());
                        l.setWrapText(true);
                        l.setMaxWidth(120);
                        acceptButton = new Button("Accept");
                        acceptButton.setPrefSize(70, 26);
                        acceptButton.setStyle("-fx-background-color: #0a66cb; -fx-padding: 5px 10px;");
                        acceptButton.setTextFill(WHITE);
                        acceptButton.setOnAction(event -> {
                            d.acceptConnection(item.getEmail(), email);
                            acceptButton.setDisable(true);
                        });

                        HBox buttonsBox = new HBox();
                        buttonsBox.setSpacing(10);
                        buttonsBox.setAlignment(CENTER_RIGHT);
                        buttonsBox.getChildren().addAll(l, rejectButton, acceptButton);
                        GridPane.setColumnIndex(buttonsBox, 1);
                        gridPane.getChildren().addAll(buttonsBox, userInfoLabel);
                    }
                }
                case "rejected" -> {
                    if (item.getMyRole().equals("sender")) {
                        disconnectButton = new Button("Delete");
                        disconnectButton.setPrefSize(80, 26);
                        disconnectButton.setStyle("-fx-background-color: #0a66cb; -fx-padding: 5px 10px;");
                        disconnectButton.setTextFill(WHITE);
                        disconnectButton.setOnAction(event -> {
                            d.deleteRequest(email, item.getEmail());
                            getListView().getItems().remove(item);
                        });
                        Label l = new Label("Request was rejected");
                        l.setFont(new Font(11));
                        l.setStyle("-fx-text-fill: RED");
                        Label ll = new Label("note: " + getItem().getNote());
                        ll.setWrapText(true);
                        ll.setMaxWidth(120);
                        HBox buttonsBox = new HBox();
                        buttonsBox.setSpacing(10);
                        buttonsBox.setAlignment(CENTER_RIGHT);
                        buttonsBox.getChildren().addAll(l, ll, disconnectButton);
                        GridPane.setColumnIndex(buttonsBox, 1);
                        gridPane.getChildren().addAll(buttonsBox, userInfoLabel);
                    }
                }
            }
            setGraphic(gridPane);
        }
    }
}
