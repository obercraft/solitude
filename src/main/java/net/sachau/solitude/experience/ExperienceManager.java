package net.sachau.solitude.experience;

import net.sachau.solitude.engine.Autowired;
import net.sachau.solitude.engine.Component;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.model.Attribute;

@Component
public class ExperienceManager {

    private final GameEngine gameEngine;

    private ExperienceGrid experienceGrid;

    @Autowired
    public ExperienceManager(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void init() {
        this.experienceGrid = new ExperienceGrid(gameEngine.getPlayer());
    }

    public ExperienceGrid getExperienceGrid() {
        return experienceGrid;
    }

    public void increase(Attribute attribute) {
        this.experienceGrid.increaseSkill(attribute);
    }

    public void decrease(Attribute attribute) {
        this.experienceGrid.decreaseSkill(attribute);
    }

    public Integer getValue(Attribute attribute) {
        return this.experienceGrid.getIncreasedAttributes().get(attribute);
    }


    public boolean increaseAllowed(Attribute attribute) {
        return this.experienceGrid.increaseAllowed(attribute);
    }

    public boolean decreaseAllowed(Attribute attribute) {
        return this.experienceGrid.decreaseAllowed(attribute);
    }

    public int getAvailableExperience() {
        return this.experienceGrid.getAvailableExperience();
    }
}
