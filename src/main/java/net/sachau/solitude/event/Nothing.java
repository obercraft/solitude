package net.sachau.solitude.event;

import net.sachau.solitude.card.EventCard;
import net.sachau.solitude.engine.GameEngine;

public class Nothing extends EventCard {

    public Nothing() {
        super("event.empty");
    }

    @Override
    public void execute(GameEngine gameEngine) {
        gameEngine.sendError("NOTHING!");
    }
}
