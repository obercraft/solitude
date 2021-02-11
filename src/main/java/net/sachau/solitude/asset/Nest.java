package net.sachau.solitude.asset;

import net.sachau.solitude.Messages;
import net.sachau.solitude.enemy.Enemy;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.gui.Icons;

public class Nest extends Asset {

    public Nest() {
        super(Messages.get("asset.nest"), Icons.Name.LOCKER);
    }

    @Override
    public void use(GameEngine gameEngine) {

    }

    @Override
    public void executeEndOfTurn(GameEngine gameEngine) {
        gameEngine.addEnemy(Enemy.Type.TYPE1);
    }
}
