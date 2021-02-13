package net.sachau.solitude.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import net.sachau.solitude.ComponentManager;
import net.sachau.solitude.asset.Asset;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.model.Door;
import net.sachau.solitude.model.Room;
import net.sachau.solitude.model.Space;
import net.sachau.solitude.text.TextNode;

import java.util.Set;

public class DoorView extends VBox {

    private final GameEngine gameEngine;


    private StringProperty value = new SimpleStringProperty();
    private TextFlow label = new TextFlow();

    private Door door;

    public DoorView(GameEngine gameEngine, Door door) {
        super();
        this.door = door;
        this.gameEngine = gameEngine;
        if (!this.door.isBlank()) {
            this.getStyleClass()
                    .add("black");

        }

        int height = MissionMapView.roomHeight;
        int width = MissionMapView.roomWidth;

        if (!this.door.isVertical()) {
            width = MissionMapView.corridorSize;
        } else {
            height = MissionMapView.corridorSize;
        }

        this.setMinSize(width, height);
        this.setMaxSize(width, height);

        getChildren().addAll(label);
        update(this.door);

//        value.addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                label.getChildren().removeAll();
//                TextFlow newText = CardText.builder()
//                        .add(newValue)
//                        .write();
//                label.getChildren().addAll(newText.getChildren());
//            }
//        });

        setOnMouseClicked(event -> {
            event.consume();
            gameEngine.openDoor(getDoor());
        });
    }

    public void update(Door door) {
        if (getDoor() != null && !door.getClass()
                .equals(getDoor().getClass())) {
            throw new RuntimeException("illegal");
        }
        this.door = door;
        String valueString;
        if (getDoor().isBlank()) {
            valueString = "";
        } else if (door.isClosed()){
            valueString = "C";
        } else {
            valueString = "O";
        }
        //value.set(this.space.getClass().getSimpleName() + ":" + valueString);
        value.set(valueString);

        label.getChildren()
                .clear();
        TextFlow newText = TextNode.builder()
                .styleOn("font", "standard")
                .styleOn("size", "16")
                .add(valueString)
                //.symbol(Symbol.FA_ARROWS_ALT_V.getText())
                .write();
        label.getChildren()
                .addAll(newText.getChildren());
    }


    public Door getDoor() {
        return this.door;
    }
}
