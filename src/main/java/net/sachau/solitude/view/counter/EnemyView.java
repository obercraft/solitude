package net.sachau.solitude.view.counter;

import net.sachau.solitude.ComponentManager;
import net.sachau.solitude.enemy.Enemy;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.model.Counter;
import net.sachau.solitude.view.MissionMapView;

public class EnemyView extends CounterView {

    Enemy enemy;
    public EnemyView(Enemy enemy) {
        super(enemy);

        setOnMouseClicked(event -> {
            event.consume();
            GameEngine gameEngine = ComponentManager.getInstance()
                    .getBean(GameEngine.class);
            gameEngine.attack(enemy);
        });


    }

    @Override
    public void update(Counter counter) {
        if (counter == null) {
            return;
        }
        this.enemy = (Enemy) counter;
        int x = enemy
                .getX();
        int y = enemy
                .getY();
        relocate(MissionMapView.roomX(x), MissionMapView.roomY(y));
        String value;
        if (!enemy.isRevealed()) {
            value = "?";
        } else {
            value = getEnemyStatString(enemy);
        }
        counterPanel.setText(value);
    }

    private String getEnemyStatString(Enemy enemy) {
        return enemy.getAttack() + " - " + enemy.getDamage() + " - " + enemy.getHits();
    }

}
