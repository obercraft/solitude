package net.sachau.solitude.view.counter;

import javafx.event.EventHandler;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import net.sachau.solitude.engine.Autowired;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.engine.View;
import net.sachau.solitude.model.Counter;
import net.sachau.solitude.model.Player;

import java.util.HashMap;
import java.util.Map;

@View
public class PlayerView extends CounterView {

    public static final DataFormat playerDataFormat = new DataFormat("player");

    private static final int size = 60;

    private final GameEngine gameEngine;

    private Player player;

    @Autowired
    public PlayerView(GameEngine gameEngine) {
        super(gameEngine.getPlayer());
        this.player = gameEngine.getPlayer();
        this.gameEngine = gameEngine;


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

    @Override
    public void update(Counter counter) {
        if (counter == null) {
            return;
        }
        this.player = (Player) counter;
        counterPanel.setText(player.getStatString());

    }
}
