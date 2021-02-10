package net.sachau.solitude.assets;

import net.sachau.solitude.Messages;
import net.sachau.solitude.enemies.Enemy;
import net.sachau.solitude.engine.Event;
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
