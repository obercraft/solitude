package net.sachau.solitude.cards;

import net.sachau.solitude.engine.GameEngine;

public abstract class EventCard extends Card {

    public EventCard() {
        super();
    }

    public abstract void execute(GameEngine gameEngine);
}
