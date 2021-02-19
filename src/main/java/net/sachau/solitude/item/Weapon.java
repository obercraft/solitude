package net.sachau.solitude.item;

import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.view.Icons;

public abstract class Weapon extends Item {

    private int damage;
    private int range;
    private boolean usesAmmo;

    public Weapon(String name, Icons.Name iconName, int damage, int range, boolean usesAmmo) {
        super(name, iconName);
        this.damage = damage;
        this.range = range;
        this.usesAmmo = usesAmmo;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public boolean isUsesAmmo() {
        return usesAmmo;
    }

    public void setUsesAmmo(boolean usesAmmo) {
        this.usesAmmo = usesAmmo;
    }

    @Override
    public void use(GameEngine gameEngine) {
        // nothing
    }
}
