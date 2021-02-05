package net.sachau.solitude.items;

import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.text.Symbol;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public abstract class Item implements Serializable {

    public enum Location {
        BODY,
        HAND,
        BOTH_HANDS,
        STASH,
    }

    private String name;
    private Symbol symbol;
    private Set<Location> allowedLocations = new HashSet<>();

    public Item(String name, Symbol symbol) {
        this.name = name;
        this.symbol = symbol;
        this.allowedLocations.add(Location.STASH);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void use(GameEngine gameEngine);

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public Set<Location> getAllowedLocations() {
        return allowedLocations;
    }
    public void addAllowLocation(Location location) {
        allowedLocations.add(location);
    }
}
