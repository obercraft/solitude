package net.sachau.solitude.event;

import net.sachau.solitude.card.EventCard;
import net.sachau.solitude.enemy.Enemy;
import net.sachau.solitude.engine.GameEngine;

public class CreateEnemyType1 extends EventCard {

    public CreateEnemyType1() {
        super("event.enemy1");
    }

    @Override
    public void execute(GameEngine gameEngine) {
        gameEngine.addEnemy(Enemy.Type.TYPE1);
    }
}
