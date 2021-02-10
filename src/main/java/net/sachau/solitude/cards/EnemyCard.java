package net.sachau.solitude.cards;

import net.sachau.solitude.enemies.Enemy;

public class EnemyCard extends Card {

    private Class<? extends Enemy> enemyType;

    public EnemyCard(Class<? extends Enemy> enemyType) {
        this.enemyType = enemyType;
    }

    public Class<? extends Enemy> getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(Class<? extends Enemy> enemyType) {
        this.enemyType = enemyType;
    }
}
