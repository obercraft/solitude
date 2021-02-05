package net.sachau.solitude.items;

import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.text.Symbol;

public abstract class Armor extends Item {

    private int protection;

    public Armor(String name, int protection) {
        super(name, Symbol.FA_FIST_RAISED);
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
