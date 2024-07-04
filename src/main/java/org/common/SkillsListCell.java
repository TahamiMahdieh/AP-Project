package org.common;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SkillsListCell extends ListCell<Education> {
    private String email;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private VBox vBox;
    private Button deleteButton;
    private Button editButton;


    public SkillsListCell(String email, ObjectInputStream reader, ObjectOutputStream writer) {
        this.email = email;
        this.writer = writer;
        this.reader = reader;
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
    public void updateItem (Education item, boolean empty){
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null); // don't display anything
        }
        else if ( item.getSkills() == null ){
            setGraphic(null);
        }
        else {
            ArrayList<String> skills = item.getSkills();
            String skillsString = "";
            for (String skill: skills){
                skillsString += skill + "\n";
            }
            Label label = new Label("🔵 " + item.getSchoolName() + " : " + skillsString);
            vBox.getChildren().add(label);
            setGraphic(vBox);
        }
    }
}
