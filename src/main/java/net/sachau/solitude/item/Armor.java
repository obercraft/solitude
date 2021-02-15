package net.sachau.solitude.item;

import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.gui.Icons;
import net.sachau.solitude.text.Symbol;

public abstract class Armor extends Item implements Wieldable {

    private int protection;
    private Boolean wielded;

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

    public void toggleOn(GameEngine gameEngine) {
        if (wielded.equals(Boolean.TRUE)) {
            return;
        } else {
            wielded = true;
            wield(gameEngine);
        }
    }

    public void toggleff(GameEngine gameEngine) {
        if (wielded == null || wielded.equals(Boolean.FALSE)) {
            return;
        } else {
            wielded = false;
            unwield(gameEngine);
        }
    }

}
