package net.sachau.solitude.gui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.sachau.solitude.ComponentManager;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.enemy.Enemy;

public class EnemyView extends StackPane {

    private static int size = 60;
    Text text = new Text();
    Enemy enemy;

    public EnemyView(Enemy enemy) {
        super();
        this.setHeight(size);
        this.setWidth(size);
        this.setMinSize(size, size);
        this.setMaxSize(size, size);

        ImageView imageView = new ImageView(Icons.get(enemy.getIcon()));
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        getChildren().add(imageView);
        update(enemy);

        setOnMouseClicked(event -> {
            event.consume();
            GameEngine gameEngine = ComponentManager.getInstance()
                    .getBean(GameEngine.class);
            gameEngine.attack(enemy);
        });


    }

    public void update(Enemy enemy) {
        this.enemy = enemy;
        int x = enemy
                .getX();
        int y = enemy
                .getY();
        relocate(MissionMapView.roomX(x), MissionMapView.roomY(y));
        String value;
        if (!enemy.isRevealed()) {
            value = "?";
        } else {
            value = "E";
        }
        if (enemy.isAlerted()) {
            value +="!";
        }
        text.setText(value);
    }

}
