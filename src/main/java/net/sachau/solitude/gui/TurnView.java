package net.sachau.solitude.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.sachau.solitude.engine.*;
import net.sachau.solitude.model.Player;

import java.util.Observable;
import java.util.Observer;

@View
public class TurnView extends HBox implements Observer {

    private final GameEngine gameEngine;


    Button nextTurn = new Button("NEXT");


    HBox hands = new HBox();
    Text playerStatus = new Text();


    @Autowired
    public TurnView(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        gameEngine.addObserver(this);
        setMinHeight(80);
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        nextTurn.setOnMouseClicked(event -> {
            gameEngine.send(Event.NEXT_TURN);
        });

        getChildren().addAll(playerStatus, nextTurn);

        update();
    }

    public void update() {
        Player player = gameEngine.getPlayer();
        if (player != null) {
            StringBuilder sb =  new StringBuilder();
            sb
                    .append("[").append(player.getY()).append(",").append(player.getX()).append("]")
                    .append(" / ")
                    .append("Actions: ").append(player.getActions())
                    .append(" / ")
                    .append("Hits: ").append(player.getHits())
                    .append(" / ")
                    .append("Turn: ").append(player.getTurn());

            playerStatus.setText(sb.toString());
        }
    }



    @Override
    public void update(Observable o, Object arg) {
        switch (Events.getType(arg)) {
            case STATUS_BAR_UPDATE:
            case PLAYER_UPDATE:
                update();
                return;
        }
    }
}
