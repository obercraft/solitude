package net.sachau.solitude.item;

import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.gui.Icons;
import net.sachau.solitude.text.Symbol;

public abstract class Armor extends Item {

    private int protection;

    public Armor(String name, int protection) {
        super(name, Icons.Name.ARMOR);
        this.addAllowLocation(Location.BODY);
        this.protection = protection;
    }

    @Override
    public void use(GameEngine gameEngine) {
        // nothing
    }

    public int getProtection() {
        return protection;
    }

    public void setProtection(int protection) {
        this.protection = protection;
    }
}
