package net.sachau.solitude.enemy;

public class EnemyFactory {

    public static Enemy create(Enemy.Type type, int y, int x) {

        Enemy enemy;
        switch (type) {
            default:
            case TYPE1: {
                enemy = new Type1(y, x);
                break;
            }
        }

        enemy.setCreated(true);
        return enemy;

    }
}
