package org.common;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class LikesTextCell extends ListCell<String> {

    private Label label;

    @Override
    public void updateItem (String item, boolean empty){
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null); // don't display anything
        }
        else {
            label = new Label(item);
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.TOP_LEFT);
            hBox.getChildren().addAll(label);
            setGraphic(hBox);
        }
    }
}
