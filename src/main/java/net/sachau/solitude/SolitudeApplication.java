package net.sachau.solitude;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.sachau.solitude.engine.Event;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.gui.*;
import net.sachau.solitude.item.KevlarVest;
import net.sachau.solitude.item.Pistol;
import net.sachau.solitude.model.GameState;
import net.sachau.solitude.mission.Mission1;
import net.sachau.solitude.mission.Mission;
import net.sachau.solitude.model.Player;

public class SolitudeApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ComponentManager componentManager = ComponentManager.getInstance();
        componentManager.initComponents();

        GameEngine gameEngine = componentManager.getBean(GameEngine.class);

        Player player = new Player();
        player.setHits(Player.MAX_HITS);
        player.setAmmo(2);
        player.addStash(new KevlarVest());
        player.addStash(new Pistol());
        Mission mission1 = new Mission1();
        mission1.generateMap(gameEngine);
        mission1.getActionCards().shuffle();
        GameState gameState = new GameState(player, mission1);

        gameEngine.setGameState(gameState);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();


        componentManager.initViews();


        GameboardView gameboardView = ComponentManager.getInstance().getBean(GameboardView.class);
        Scene scene = new Scene(gameboardView, primaryScreenBounds.getWidth() -100, primaryScreenBounds.getHeight() -100);
        scene.getStylesheets().add(this.getClass().getResource("/application.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
        gameEngine.send(Event.TEXT_NODE, "intro");
        gameboardView.requestFocus();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
