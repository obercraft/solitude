package net.sachau.solitude.assets;

import net.sachau.solitude.engine.Event;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.model.Asset;
import net.sachau.solitude.text.Symbol;

public class Locker extends Asset {

    public Locker() {
        super("Locker", Symbol.FA_HAND_HOLDING);
    }

    @Override
    public void use(GameEngine gameEngine) {
        if (gameEngine.getMission().getItemChits().hasChits()) {
            gameEngine.getPlayer().useAction();
            gameEngine.getPlayer().getStash().add(gameEngine.getMission().getItemChits().draw());
            gameEngine.send(Event.PLAYER_UPDATE_ITEMS);
        } else {
            gameEngine.sendError("no more items");
        }
    }
}
