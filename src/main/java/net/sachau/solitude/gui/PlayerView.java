package net.sachau.solitude.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
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

    private final GameEngine gameEngine;
    Text label = new Text();
    StringProperty value = new SimpleStringProperty();

    @Autowired
    public PlayerView(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        this.setHeight(40);
        this.setWidth(40);
        this.setMinSize(40, 40);
        this.setMaxSize(40,40);
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        label.textProperty().bind(value);
        getChildren().add(label);

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
