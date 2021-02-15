package net.sachau.solitude.item;

import net.sachau.solitude.Messages;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.model.Attribute;
import net.sachau.solitude.model.Player;

public class KevlarVest extends Armor {


    public KevlarVest() {
        super(Messages.get("item.kevlar-vest"),3);
    }

    @Override
    public void wield(GameEngine gameEngine) {
        Player player = gameEngine.getPlayer();
        player.addBonus(Attribute.ATTACK, 5);
    }

    @Override
    public void unwield(GameEngine gameEngine) {
        Player player = gameEngine.getPlayer();
        player.addBonus(Attribute.ATTACK, -5);

    }
}
