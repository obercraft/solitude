package net.sachau.solitude.gui;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.sachau.solitude.engine.*;
import net.sachau.solitude.items.Item;

import java.util.Observable;
import java.util.Observer;

@View
public class ItemView extends VBox implements Observer {

    private final GameEngine gameEngine;

    HBox hands = new HBox();
    FlowPane stash = new FlowPane();


    @Autowired
    public ItemView(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        gameEngine.addObserver(this);
        setMinSize(300,300);
        setMaxSize(300,300);
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        getChildren().addAll(hands, stash);

    }

    public void update() {
        stash.getChildren().clear();
        for (Item item : gameEngine.getPlayer().getStash()) {
            stash.getChildren().add(new Text(item.getName()));
        }
    }



    @Override
    public void update(Observable o, Object arg) {
        switch (Events.getType(arg)) {
            case PLAYER_UPDATE_ITEMS:
                update();
                gameEngine.send(Event.STATUS_BAR_UPDATE);
                return;
        }
    }
}
