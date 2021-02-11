package net.sachau.solitude.enemy;

public class EnemyFactory {

    public static Enemy create(Enemy.Type type, int y, int x) {

        Enemy enemy = new Enemy(y, x);
        switch (type) {
            default:
            case TYPE1: {
                enemy.setAttack(0);
                enemy.setDamage(1);
                enemy.setHits(2);
            }
        }

        enemy.setCreated(true);
        return enemy;

    }
}
