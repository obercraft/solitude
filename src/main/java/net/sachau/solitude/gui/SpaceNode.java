package net.sachau.solitude.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import net.sachau.solitude.ComponentManager;
import net.sachau.solitude.asset.Asset;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.model.*;
import net.sachau.solitude.text.TextNode;

import java.util.Set;

public class SpaceNode extends ScrollPane {

    private final GameEngine gameEngine;


    StringProperty value = new SimpleStringProperty();
    TextFlow label = new TextFlow();
    FlowPane content = new FlowPane();
    FlowPane properties = new FlowPane();
    private VBox spaceArea = new VBox();

    Space space;

    public SpaceNode(GameEngine gameEngine, Space space) {
        super();
        setContent(spaceArea);
        this.getStyleClass().add("steel");
        label.getStyleClass().add("steel");
        content.getStyleClass().add("steel");
        properties.getStyleClass().add("steel");
        spaceArea.getStyleClass().add("steel");



        this.gameEngine = gameEngine;
        this.space = space;
        int height = MissionMapView.roomHeight;
        int width = MissionMapView.roomWidth;
        if (space instanceof Door) {
            Door d = (Door) space;
            if (!d.isVertical()) {
                width = MissionMapView.corridorSize;
            } else {
                height = MissionMapView.corridorSize;
            }

        }

        this.setMinSize(width, height);
        this.setMaxSize(width,height);

        if (space instanceof Room){
            Room room = (Room) space;
            Set<Asset> roomProperties = room.getAssets();
            if (roomProperties != null) {
                for (Asset asset : roomProperties) {
                    properties.getChildren().add(new AssetNode(room, this.gameEngine, asset));
                }
            }
        }

        spaceArea.getChildren().addAll(label, content, properties);
        update(space);

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
                if (!getSpace().isBlank() && event.getDragboard()
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
                GameEngine gameEngine = ComponentManager.getInstance().getBean(GameEngine.class);
                gameEngine.moveTo(getSpace());
            }
        });

        setOnMouseClicked(event -> {
            event.consume();
            System.out.println(getSpace());
            if (getSpace() instanceof Door) {
                gameEngine.openDoor((Door) getSpace());
            }

        });



    }

    public void update(Space space) {
        if (this.space != null && !space.getClass().equals(this.space.getClass())) {
            throw new RuntimeException("illegal");
        }
        this.space = space;
        String valueString;
        if (this.space.isBlank()) {
            valueString = "";
        } else if (this.space instanceof Door) {
            if (!this.space.isDiscovered()) {
                valueString = "?";
            } else if (this.space.isLocked()) {
                valueString = "L";
            } else if (this.space.isClosed()) {
                valueString = "C";
            } else {
                valueString ="O";
            }
        } else if (this.space instanceof Room) {
            valueString = "Room";
        } else {
            valueString = "Unknown";
        }
        //value.set(this.space.getClass().getSimpleName() + ":" + valueString);
        value.set(valueString);

        label.getChildren().clear();
        TextFlow newText = TextNode.builder().styleOn("font", "standard").styleOn("size", "16")
                .add(valueString)
                //.symbol(Symbol.FA_ARROWS_ALT_V.getText())
                .write();
        label.getChildren().addAll(newText.getChildren());
    }

    public void addContent(Node node) {
        content.getChildren().add(node);
    }
    public void removeContent(Node node) {
        content.getChildren().remove(node);
    }

    public Space getSpace() {
        return space;
    }
}
