package net.sachau.solitude.model;

import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.gui.Icons;
import net.sachau.solitude.text.Symbol;

public abstract class Asset {

    private String name;
    // -1 is unlimited
    private int uses = -1;

    private Icons.Name icon;

    public Asset(String name, Icons.Name icon) {
        this.name = name;
        this.icon  = icon;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    public abstract void use(GameEngine gameEngine);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icons.Name getIcon() {
        return icon;
    }

    public void setIcon(Icons.Name icon) {
        this.icon = icon;
    }
}
