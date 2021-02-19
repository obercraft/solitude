package net.sachau.solitude.view;

import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ItemView extends VBox {

    public static final DataFormat itemDataFormat = new DataFormat("item");

    private final GameEngine gameEngine;

    private static final int size = 50;
    Tooltip toolTip = new Tooltip();
    private Item item;
    private Item.Location location;


    public ItemView(GameEngine gameEngine, Item.Location location, Item item) {
        super();
        this.gameEngine = gameEngine;
        this.item = item;
        this.location = location;

        this.setMinSize(size, size);
        this.setMaxSize(size, size);
        this.getStyleClass().add("border");

        if (this.item !=  null) {
            ImageView imageView = new ImageView(Icons.get(item.getIcon()));
            imageView.setFitWidth(size);
            imageView.setFitHeight(size);

            getChildren().add(imageView);
            toolTip.setText(item.getName());
            Tooltip.install(this, toolTip);

            this.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Dragboard db = startDragAndDrop(TransferMode.ANY);
                    Map<DataFormat, Object> map = new HashMap<>();
                    map.put(itemDataFormat, item);
                    db.setContent(map);
                    event.consume();
                }
            });
        }

        this.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.consume();
                if (event.getDragboard()
                        .hasContent(itemDataFormat)) {
                    Item it = (Item) event.getDragboard().getContent(itemDataFormat);
                    if (it == null) {
                        return;
                    } else {
                        if (it.getAllowedLocations() != null && it.getAllowedLocations().contains(location)) {
                            event.acceptTransferModes(TransferMode.ANY);
                        }
                    }

                }
            }
        });
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.consume();
                Item it = (Item) event.getDragboard().getContent(itemDataFormat);
                gameEngine.moveItem(it, getItem(), getLocation());

            }
        });

    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item.Location getLocation() {
        return location;
    }

    public void setLocation(Item.Location location) {
        this.location = location;
    }

}

