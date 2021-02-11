package net.sachau.solitude.gui;

import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import net.sachau.solitude.engine.Autowired;
import net.sachau.solitude.engine.Event;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.engine.View;

@View
public class GameboardView extends VBox {

    private final GameEngine gameEngine;
    private final ConsoleView consoleView;
    private final MissionMapView missionMapView;
    private final TurnView turnView;
    private final ItemView itemView;

    @Autowired
    public GameboardView(GameEngine gameEngine, ConsoleView consoleView, MissionMapView missionMapView, TurnView turnView, ItemView itemView) {
        super();
        this.gameEngine = gameEngine;
        this.consoleView = consoleView;
        this.missionMapView = missionMapView;
        this.turnView = turnView;
        this.itemView = itemView;
        this.getStyleClass().add("steel");


        HBox box2 = new HBox();
        box2.getChildren().addAll(this.missionMapView, this.itemView);


        getChildren().addAll(box2, this.turnView, this.consoleView);

        setOnKeyPressed(event -> {
            event.consume();
            if (event.getCode() == KeyCode.SPACE) {
                this.gameEngine.send(Event.NEXT_TURN);
            }
        });


    }
}
