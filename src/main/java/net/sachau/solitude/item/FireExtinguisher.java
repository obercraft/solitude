package net.sachau.solitude.item;

import net.sachau.solitude.Messages;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.gui.Icons;
import net.sachau.solitude.text.Symbol;

public class FireExtinguisher extends Item{

    public FireExtinguisher() {
        super(Messages.get("item.fire-extinguisher"), Icons.Name.FIRE_EXTINGUISHER);
        addAllowLocation(Location.HAND);
    }

    @Override
    public void use(GameEngine gameEngine) {

    }
}
