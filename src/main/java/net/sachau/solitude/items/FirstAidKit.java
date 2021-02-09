package net.sachau.solitude.items;

import net.sachau.solitude.Messages;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.model.Player;
import net.sachau.solitude.text.Symbol;

public class FirstAidKit extends Item {

    public FirstAidKit() {
        super(Messages.get("item.first-aid-kit"), Symbol.FA_FIRST_AID);
    }

    @Override
    public void use(GameEngine gameEngine) {
        int health = gameEngine.getPlayer().getHits() + 2;
        gameEngine.getPlayer().setHits(Math.max(health, Player.MAX_HITS));
    }
}
