package net.sachau.solitude.experience;

import net.sachau.solitude.model.Attribute;
import net.sachau.solitude.model.Player;

import java.util.HashMap;
import java.util.Map;

public class ExperienceGrid {

    private int availableExperience;

    private Map<Attribute, Integer> initalAttributes = new HashMap<>();
    private Map<Attribute, Integer> increasedAttributes = new HashMap<>();

    public ExperienceGrid(Player player) {
        this.availableExperience = 5;

        for (Attribute attribute : Attribute.values()) {
            initalAttributes.put(attribute, player.getAttribute(attribute));
            increasedAttributes.put(attribute, player.getAttribute(attribute));
        }
    }

    public boolean increaseAllowed(Attribute attribute) {
        int currentValue = increasedAttributes.get(attribute);
        int increasedValue = 1 + increasedAttributes.get(attribute);
        return this.availableExperience >= increasedValue * attribute.getCost();
    }

    public boolean decreaseAllowed(Attribute attribute) {
        int initialValue = initalAttributes.get(attribute);
        int currentValue = increasedAttributes.get(attribute);
        return currentValue > initialValue;
    }

    public void increaseSkill(Attribute attribute) {
        if (!increaseAllowed(attribute)) {
            return;
        } else {
            int increasedValue = 1 + increasedAttributes.get(attribute);
            this.increasedAttributes.put(attribute, increasedValue);
            this.availableExperience -= increasedValue * attribute.getCost();
        }
    }

    public void decreaseSkill(Attribute attribute) {
        if (!decreaseAllowed(attribute)) {
            return;
        } else {
            int currentValue = increasedAttributes.get(attribute);
            int decreaseValue = increasedAttributes.get(attribute) -1;
            this.increasedAttributes.put(attribute, decreaseValue);
            this.availableExperience += currentValue * attribute.getCost();
        }
    }

    public Map<Attribute, Integer> getIncreasedAttributes() {
        return increasedAttributes;
    }

    public int getAvailableExperience() {
        return availableExperience;
    }
}
