package net.sachau.solitude.model;

import net.sachau.solitude.Messages;

public enum Attribute {

    HITS(Messages.get("attribute.hits"), 1),
    ATTACK(Messages.get("attribute.attack"), 2),
    STEALTH(Messages.get("attribute.stealth"), 1)

    ;

    private String name;
    private int cost;

    Attribute(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
