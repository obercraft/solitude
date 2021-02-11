package net.sachau.solitude.card;

import net.sachau.solitude.enemy.Enemy;

public class EnemyCard extends Card {

    private Class<? extends Enemy> enemyType;

    public EnemyCard(String name, Class<? extends Enemy> enemyType) {
        super(name);
        this.enemyType = enemyType;
    }

    public Class<? extends Enemy> getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(Class<? extends Enemy> enemyType) {
        this.enemyType = enemyType;
    }
}
