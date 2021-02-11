package net.sachau.solitude.card;

import net.sachau.solitude.engine.GameEngine;

public abstract class EventCard extends Card {

    public EventCard(String name) {
        super(name);
    }

    public abstract void execute(GameEngine gameEngine);
}
