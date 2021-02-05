package net.sachau.solitude.model;

import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.text.Symbol;

public abstract class Asset {

    private String name;
    // -1 is unlimited
    private int uses = -1;

    private Symbol symbol;

    public Asset(String name, Symbol symbol) {
        this.name = name;
        this.symbol = symbol;
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

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}
