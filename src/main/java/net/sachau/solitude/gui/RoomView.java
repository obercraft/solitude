package net.sachau.solitude.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
import net.sachau.solitude.ComponentManager;
import net.sachau.solitude.asset.Asset;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.model.*;
import net.sachau.solitude.text.TextNode;

import java.util.Set;

public class RoomView extends VBox {

    private final GameEngine gameEngine;


    private StringProperty value = new SimpleStringProperty();
    private TextFlow label = new TextFlow();
    private FlowPane content = new FlowPane();
    private ScrollPane scrollArea = new ScrollPane();

    private Room room;

    public RoomView(GameEngine gameEngine, Room room) {
        super();
        this.gameEngine = gameEngine;
        this.room = room;
        this.getStyleClass()
                .add("steel");
        scrollArea.getStyleClass()
                .add("steel");

        if (!this.room.isBlank()) {
            this.getStyleClass()
                    .add("border");
        }

        label.getStyleClass()
                .add("steel");
        label.setMinHeight(20);
        label.setMaxHeight(20);
        content.getStyleClass()
                .add("steel");

        int height = MissionMapView.roomHeight;
        int width = MissionMapView.roomWidth;

        this.setMinSize(width, height);
        this.setMaxSize(width, height);

        content.setPrefSize(width, height);
        scrollArea.setContent(content);
        scrollArea.setFitToHeight(true);
        scrollArea.setFitToWidth(true);

        Set<Asset> roomProperties = this.room.getAssets();
        if (roomProperties != null) {
            for (Asset asset : roomProperties) {
                content.getChildren()
                        .add(new AssetNode(room, this.gameEngine, asset));
            }
        }

        if (!this.room.isBlank()) {
            getChildren().addAll(label, scrollArea);
        }
        update(this.room);

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

        setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (!getRoom().isBlank() && event.getDragboard()
                        .hasContent(PlayerView.playerDataFormat)) {
                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            }
        });

        setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.consume();
                gameEngine.moveTo(getRoom());
            }
        });

    }

    public void update(Room room) {
        if (this.room != null && !room.getClass()
                .equals(this.room.getClass())) {
            throw new RuntimeException("illegal");
        }
        this.room = room;
        String valueString;
        if (this.room.isBlank()) {
            valueString = "";
        } else {
            valueString = "Room";
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

    public void addContent(Node node) {
        content.getChildren()
                .add(node);
    }

    public void removeContent(Node node) {
        content.getChildren()
                .remove(node);
    }

    public Space getRoom() {
        return room;
    }
}
