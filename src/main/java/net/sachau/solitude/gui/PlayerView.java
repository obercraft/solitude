package net.sachau.solitude.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.sachau.solitude.engine.Autowired;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.engine.View;
import net.sachau.solitude.model.Player;

import java.util.HashMap;
import java.util.Map;

@View
public class PlayerView extends VBox {

    public static final DataFormat playerDataFormat = new DataFormat("player");

    private static final int size = 60;

    private final GameEngine gameEngine;
    Text label = new Text();
    StringProperty value = new SimpleStringProperty();

    @Autowired
    public PlayerView(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        this.setHeight(size);
        this.setWidth(size);
        this.setMinSize(size, size);
        this.setMaxSize(size,size);

        label.textProperty().bind(value);
        ImageView imageView = new ImageView(Icons.get(Icons.Name.PLAYER));
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        getChildren().add(imageView);

        update();

        this.setOnMouseClicked(event -> {
            event.consume();
        });

        this.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = startDragAndDrop(TransferMode.ANY);
                Map<DataFormat, Object> map = new HashMap<>();
                map.put(playerDataFormat, gameEngine.getPlayer());
                db.setContent(map);
                event.consume();
            }
        });

    }

    public void update() {
        Player player = gameEngine.getPlayer();
        value.set(player.getActions() +"@" +player.getTurn());
    }
}
