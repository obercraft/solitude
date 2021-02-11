package net.sachau.solitude.event;

import net.sachau.solitude.asset.Burning;
import net.sachau.solitude.card.EventCard;
import net.sachau.solitude.enemy.Enemy;
import net.sachau.solitude.enemy.EnemyFactory;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.model.Room;
import net.sachau.solitude.model.Space;

public class Fire extends EventCard {

    public Fire() {
        super("event.fire");
    }

    @Override
    public void execute(GameEngine gameEngine) {

        Space space = gameEngine.getMissionMap().getSpace(gameEngine.getPlayer().getY(), gameEngine.getPlayer().getX());
        if (space instanceof Room) {
            ((Room) space).addAsset(new Burning(), space);
        }
    }
}
