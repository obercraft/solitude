package net.sachau.solitude;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.sachau.solitude.enemy.Type1;
import net.sachau.solitude.view.*;
import net.sachau.solitude.view.counter.EnemyView;

public class SolitudeApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ComponentManager componentManager = ComponentManager.getInstance();
        componentManager.initComponents();

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        componentManager.initViews();


//        EnemyView enemyView = new EnemyView(new Type1(0, 0));
        VBox content = new VBox();
        content.setMinSize(500,500);
        content.getStyleClass().add("room");
//        content.getChildren().add(enemyView);
//        Scene scene = new Scene(content, primaryScreenBounds.getWidth() -100, primaryScreenBounds.getHeight() -100);

        Type1 type1 = new Type1(0, 0);
        type1.setRevealed(true);
        type1.setAlerted(true);
        EnemyView enemyView = new EnemyView(type1);
        content.getChildren().add(enemyView);
        Scene scene = new Scene(ComponentManager.getInstance().getBean(MainView.class), primaryScreenBounds.getWidth() -100, primaryScreenBounds.getHeight() -100);
        //Scene scene = new Scene(content, primaryScreenBounds.getWidth() -100, primaryScreenBounds.getHeight() -100);
        scene.getStylesheets().add(this.getClass().getResource("/application.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

}
