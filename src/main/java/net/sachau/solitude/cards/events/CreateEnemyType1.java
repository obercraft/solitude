package net.sachau.solitude.cards.events;

import net.sachau.solitude.cards.EventCard;
import net.sachau.solitude.enemies.Enemy;
import net.sachau.solitude.engine.GameEngine;

public class CreateEnemyType1 extends EventCard {

    public CreateEnemyType1() {
    }

    @Override
    public void execute(GameEngine gameEngine) {
        gameEngine.addEnemy(Enemy.Type.TYPE1);
    }
}
