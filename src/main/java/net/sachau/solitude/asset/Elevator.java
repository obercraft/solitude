package net.sachau.solitude.asset;

import net.sachau.solitude.Messages;
import net.sachau.solitude.engine.Event;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.gui.Icons;

public class Elevator extends Asset {

    public Elevator() {
        super(Messages.get("asset.elevator"), Icons.Name.ELEVATOR);
    }

    @Override
    public void use(GameEngine gameEngine) {
        gameEngine.send(Event.PLAYER_USED, this);
    }

    @Override
    public void executeEndOfTurn(GameEngine gameEngine) {
        // nothing
    }
}
