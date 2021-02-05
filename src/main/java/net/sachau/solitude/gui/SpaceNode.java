package net.sachau.solitude.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import net.sachau.solitude.ComponentManager;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.model.Door;
import net.sachau.solitude.model.Room;
import net.sachau.solitude.model.Asset;
import net.sachau.solitude.model.Space;
import net.sachau.solitude.text.TextNode;
import net.sachau.solitude.text.Symbol;

import java.util.Set;

public class SpaceNode extends VBox {

    private final GameEngine gameEngine;


    StringProperty value = new SimpleStringProperty();
    TextFlow label = new TextFlow();
    FlowPane content = new FlowPane();
    FlowPane properties = new FlowPane();
    Space space;

    public SpaceNode(GameEngine gameEngine, Space space) {
        super();
        this.gameEngine = gameEngine;
        this.space = space;
        int size = MissionMapView.corridorSize;
        if (space instanceof Room) {
            size = MissionMapView.roomHeight;
        } else if (space instanceof Door) {
            size = MissionMapView.corridorSize;
        }

        this.setMinSize(size, size);
        this.setMaxSize(size,size);
        if (!space.isBlank()) {
            this.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }

        if (space instanceof Room){
            Room room = (Room) space;
            Set<Asset> roomProperties = room.getAssets();
            if (roomProperties != null) {
                for (Asset asset : roomProperties) {
                    properties.getChildren().add(new AssetNode(room, this.gameEngine, asset));
                }
            }
        }

        getChildren().addAll(label, content, properties);
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
                if (event.getDragboard()
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
                gameEngine.moveTo(space);
            }
        });

        setOnMouseClicked(event -> {
            event.consume();
            if (space instanceof Door) {
                gameEngine.openDoor((Door) space);
            }

        });



    }

    public void update(Space space) {
        if (this.space != null && !space.getClass().equals(this.space.getClass())) {
            throw new RuntimeException("illegal");
        }
        this.space = space;
        String valueString;
        if (space.isBlank()) {
            valueString = "";
        } else if (space instanceof Door) {
            if (!space.isDiscovered()) {
                valueString = "?";
            } else if (space.isLocked()) {
                valueString = "L";
            } else if (space.isClosed()) {
                valueString = "C";
            } else {
                valueString ="O";
            }
        } else if (space instanceof Room) {
            valueString = "Room";
        } else {
            valueString = "Unknown";
        }
        //value.set(this.space.getClass().getSimpleName() + ":" + valueString);
        value.set(valueString);

        label.getChildren().clear();
        TextFlow newText = TextNode.builder().styleOn("font", "standard").styleOn("size", "16")
                .add(valueString)
                .symbol(Symbol.FA_ARROWS_ALT_V.getText())
                .write();
        label.getChildren().addAll(newText.getChildren());
    }

    public void addContent(Node node) {
        content.getChildren().add(node);
    }
    public void removeContent(Node node) {
        content.getChildren().remove(node);
    }
}
