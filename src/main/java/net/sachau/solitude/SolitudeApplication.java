package net.sachau.solitude;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.sachau.solitude.engine.Event;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.gui.*;
import net.sachau.solitude.model.GameState;
import net.sachau.solitude.missions.Mission1;
import net.sachau.solitude.model.Mission;
import net.sachau.solitude.model.Player;
import net.sachau.solitude.items.Weapon;

public class SolitudeApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ComponentManager componentManager = ComponentManager.getInstance();
        componentManager.initComponents();

        GameEngine gameEngine = componentManager.getBean(GameEngine.class);

        Player player = new Player();
        player.setHits(5);
        player.setAmmo(2);
        // player.setRight(new Weapon("Pistol", 2,1, 1, true));
        Mission mission1 = new Mission1();
        mission1.generateMap(gameEngine);
        mission1.getActionCards().shuffle();
        GameState gameState = new GameState(player, mission1);

        gameEngine.setGameState(gameState);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();


        componentManager.initViews();


        GameboardView gameboardView = ComponentManager.getInstance().getBean(GameboardView.class);
        Scene scene = new Scene(gameboardView, primaryScreenBounds.getWidth() -100, primaryScreenBounds.getHeight() -100);


        primaryStage.setScene(scene);
        primaryStage.show();
        gameEngine.send(Event.TEXT_NODE, "intro");
        gameboardView.requestFocus();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
