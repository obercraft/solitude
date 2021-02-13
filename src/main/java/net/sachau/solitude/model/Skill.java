package net.sachau.solitude.model;

import net.sachau.solitude.Messages;

public enum Skill {

    ATTACK(Messages.get("skill.attack"), 2),
    STEALTH(Messages.get("skill.stealth"), 1)

    ;

    private String name;
    private int cost;

    Skill(String name, int cost) {
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
