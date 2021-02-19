package net.sachau.solitude.asset;

import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.view.Icons;
import net.sachau.solitude.model.Space;

public abstract class Asset {

    private String name;
    // -1 is unlimited
    private int uses = -1;

    private Space space;

    private Icons.Name icon;

    public Asset(String name, Icons.Name icon) {
        this.name = name;
        this.icon  = icon;
    }

    public abstract void use(GameEngine gameEngine);
    public abstract void executeEndOfTurn(GameEngine gameEngine);

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }



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

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }
}
