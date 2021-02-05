package net.sachau.solitude.assets;

import net.sachau.solitude.engine.Event;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.model.Asset;
import net.sachau.solitude.text.Symbol;

public class Elevator extends Asset {

    public Elevator() {
        super("Elevator", Symbol.FA_ARROWS_ALT);
    }

    @Override
    public void use(GameEngine gameEngine) {
        gameEngine.send(Event.PLAYER_USED, this);
    }
}
