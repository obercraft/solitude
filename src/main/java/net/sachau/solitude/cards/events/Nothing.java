package net.sachau.solitude.cards.events;

import net.sachau.solitude.cards.EventCard;
import net.sachau.solitude.enemies.Enemy;
import net.sachau.solitude.engine.GameEngine;

public class Nothing extends EventCard {

    public Nothing() {
    }

    @Override
    public void execute(GameEngine gameEngine) {
        gameEngine.sendError("NOTHING!");
    }
}
