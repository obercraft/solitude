package net.sachau.solitude.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sachau.solitude.Messages;
import net.sachau.solitude.engine.*;

import java.util.Observable;
import java.util.Observer;

@View
public class GameBox extends HBox implements Observer {

    private final GameEngine gameEngine;
    private final TextManager textManager;
    private final GameboardView gameboardView;

    @Autowired
    public GameBox(GameEngine gameEngine, TextManager textManager, GameboardView gameboardView) {
        super();
        this.gameboardView = gameboardView;
        getStyleClass().add("steel");
        this.gameEngine = gameEngine;
        this.textManager = textManager;
        gameEngine.addObserver(this);
        showStartGame();
    }
    private void showStartGame() {
        getChildren().clear();

        VBox left = new VBox();
        VBox right = new VBox();
        left.getChildren().add(textManager.getTextNode("intro").writeln());

        Button startGame = new Button(Messages.get("game.start"));
        right.getChildren().addAll(startGame);
        getChildren().addAll(left, right);

        startGame.setOnMouseClicked(event -> {
            event.consume();
            gameEngine.send(Event.START_GAME);
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        switch(Events.getType(arg)) {
            case START_GAME: {
                gameEngine.startNewGame();
                getChildren().clear();
                gameboardView.update();
                getChildren().add(gameboardView);
                return;
            }
        }
    }
}
