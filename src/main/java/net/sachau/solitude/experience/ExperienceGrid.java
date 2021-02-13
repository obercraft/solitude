package net.sachau.solitude.experience;

import net.sachau.solitude.model.Player;
import net.sachau.solitude.model.Skill;

import java.util.HashMap;
import java.util.Map;

public class ExperienceGrid {

    private int availableExperience;

    private Map<Skill, Integer> initialSkills = new HashMap<>();
    private Map<Skill, Integer> increasedSkills = new HashMap<>();

    public ExperienceGrid(Player player) {
        this.availableExperience = 5;

        for (Skill skill : Skill.values()) {
            initialSkills.put(skill, player.getSkill(skill));
            increasedSkills.put(skill, player.getSkill(skill));
        }
    }

    public boolean increaseAllowed(Skill skill) {
        int currentValue = increasedSkills.get(skill);
        int increasedValue = 1 + increasedSkills.get(skill);
        return this.availableExperience >= increasedValue * skill.getCost();
    }

    public boolean decreaseAllowed(Skill skill) {
        int initialValue = initialSkills.get(skill);
        int currentValue = increasedSkills.get(skill);
        return currentValue > initialValue;
    }

    public void increaseSkill(Skill skill) {
        if (!increaseAllowed(skill)) {
            return;
        } else {
            int increasedValue = 1 + increasedSkills.get(skill);
            this.increasedSkills.put(skill, increasedValue);
            this.availableExperience -= increasedValue * skill.getCost();
        }
    }

    public void decreaseSkill(Skill skill) {
        if (!decreaseAllowed(skill)) {
            return;
        } else {
            int currentValue = increasedSkills.get(skill);
            int decreaseValue = increasedSkills.get(skill) -1;
            this.increasedSkills.put(skill, decreaseValue);
            this.availableExperience += currentValue * skill.getCost();
        }
    }

    public Map<Skill, Integer> getIncreasedSkills() {
        return increasedSkills;
    }

    public int getAvailableExperience() {
        return availableExperience;
    }
}
