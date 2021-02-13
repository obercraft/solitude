package net.sachau.solitude;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.sachau.solitude.engine.Events;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.gui.*;

import java.util.Observable;
import java.util.Observer;

public class SolitudeApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ComponentManager componentManager = ComponentManager.getInstance();
        componentManager.initComponents();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        componentManager.initViews();

        GameBox startGameView = ComponentManager.getInstance().getBean(GameBox.class);
        Scene scene = new Scene(startGameView, primaryScreenBounds.getWidth() -100, primaryScreenBounds.getHeight() -100);
        scene.getStylesheets().add(this.getClass().getResource("/application.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

}
