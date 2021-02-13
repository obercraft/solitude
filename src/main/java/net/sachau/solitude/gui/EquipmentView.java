package net.sachau.solitude.gui;

import javafx.scene.layout.*;
import net.sachau.solitude.engine.*;
import net.sachau.solitude.item.Item;
import net.sachau.solitude.model.Player;

import java.util.Observable;
import java.util.Observer;

@View
public class EquipmentView extends VBox implements Observer {

    private final GameEngine gameEngine;

    HBox equipped = new HBox();
    FlowPane stash = new FlowPane();


    @Autowired
    public EquipmentView(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        gameEngine.addObserver(this);
        setMinSize(300,300);
        setMaxSize(300,300);

        getChildren().addAll(equipped, stash);
        update();

    }

    public void update() {
        Player player = gameEngine.getPlayer();
        if (player == null) {
            return;
        }
        equipped.getChildren().clear();
            equipped.getChildren()
                    .add(new ItemView(gameEngine, Item.Location.HAND, Item.Position.LEFT_HAND, player.getLeft()));

            equipped.getChildren().add(new ItemView(gameEngine, Item.Location.BODY, Item.Position.BODY, player.getBody()));

            equipped.getChildren()
                    .add(new ItemView(gameEngine, Item.Location.HAND, Item.Position.RIGHT_HAND, player.getRight()));


        stash.getChildren().clear();
        for (Item item : gameEngine.getPlayer().getStash()) {
            stash.getChildren().add(new ItemView(gameEngine, Item.Location.STASH, null, item));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        switch (Events.getType(arg)) {
            case UPDATE_EQUIPMENT:
                update();
                gameEngine.send(Event.STATUS_BAR_UPDATE);
                return;
        }
    }
}
