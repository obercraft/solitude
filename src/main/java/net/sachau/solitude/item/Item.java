package net.sachau.solitude.item;

import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.gui.Icons;
import net.sachau.solitude.text.Symbol;

import javax.swing.*;
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
    private Icons.Name icon;
    private Set<Location> allowedLocations = new HashSet<>();

    public Item(String name, Icons.Name icon) {
        this.name = name;
        this.icon = icon;
        this.allowedLocations.add(Location.STASH);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void use(GameEngine gameEngine);

    public Icons.Name getIcon() {
        return icon;
    }

    public void setIcon(Icons.Name icon) {
        this.icon = icon;
    }

    public Set<Location> getAllowedLocations() {
        return allowedLocations;
    }
    public void addAllowLocation(Location location) {
        allowedLocations.add(location);
    }
}
