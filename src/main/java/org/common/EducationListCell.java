package org.common;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EducationListCell extends ListCell<Education> {
    private String school;
    private String fieldOfStudy;
    private String startDate;
    private String endDate;
    private double grade;
    private String activities;
    private String description;
    private String email;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private VBox vBox;
    private Button deleteButton;
    private Button editButton;

    public EducationListCell(String email, ObjectInputStream reader, ObjectOutputStream writer) {
        this.reader = reader;
        this.writer = writer;
        this.email = email;
        vBox = new VBox();
        vBox.setPrefWidth(340);
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setSpacing(7);
        vBox.setPadding(new Insets(10, 20, 10 ,20));
        vBox.setStyle("-fx-border-color: #acacac");
        deleteButton = new Button("Delete");
        editButton = new Button("Edit");
    }

    @Override
    protected void updateItem (Education item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null); // don't display anything
        }
        else {
            school = item.getSchoolName();
            fieldOfStudy = item.getFieldOfStudy();
            startDate = item.getStartDate().toString();
            endDate = item.getEndDate().toString();
            grade = item.getGrade();
            activities = item.getActivitiesAndSocieties();
            description = item.getDescription();


            String top = school + ": " + fieldOfStudy;
            Label topLabel = new Label(top);
            topLabel.setFont(new Font(15));
            vBox.getChildren().add(topLabel);

            String body = startDate + " / " + endDate;
            Label bodyLabel = new Label(body);
            bodyLabel.setStyle("-fx-text-fill: #626161");
            bodyLabel.setFont(new Font(11));
            vBox.getChildren().add(bodyLabel);

            Label bodyLabel2 = new Label("Grade: " + grade);
            bodyLabel2.setStyle("-fx-text-fill: #626161");
            bodyLabel2.setFont(new Font(11));
            vBox.getChildren().add(bodyLabel2);

            Label bodyLabel3 = new Label("Activities an societies: " + activities);
            bodyLabel3.setStyle("-fx-text-fill: #626161");
            bodyLabel3.setFont(new Font(11));
            vBox.getChildren().add(bodyLabel3);

            Label bodyLabel4 = new Label("description: " + description);
            bodyLabel4.setStyle("-fx-text-fill: #626161");
            bodyLabel4.setFont(new Font(11));
            vBox.getChildren().add(bodyLabel4);

            deleteButton.setOnAction(event -> {

            });

            editButton.setOnAction(event -> {

            });

            HBox hBox = new HBox(deleteButton, editButton);
            hBox.setSpacing(5);
            hBox.setAlignment(Pos.BOTTOM_RIGHT);
            vBox.getChildren().add(hBox);
            setGraphic(vBox);
        }
    }

}
