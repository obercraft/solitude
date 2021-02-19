package net.sachau.solitude.item;

import net.sachau.solitude.Messages;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.view.Icons;

public class FireExtinguisher extends Item{

    public FireExtinguisher() {
        super(Messages.get("item.fire-extinguisher"), Icons.Name.FIRE_EXTINGUISHER);
        addAllowLocation(Location.HANDS);
    }

    @Override
    public void use(GameEngine gameEngine) {

    }
}
