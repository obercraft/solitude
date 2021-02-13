package net.sachau.solitude.experience;

import net.sachau.solitude.engine.Autowired;
import net.sachau.solitude.engine.Component;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.model.Skill;

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

    public void increase(Skill skill) {
        this.experienceGrid.increaseSkill(skill);
    }

    public void decrease(Skill skill) {
        this.experienceGrid.decreaseSkill(skill);
    }

    public Integer getValue(Skill skill) {
        return this.experienceGrid.getIncreasedSkills().get(skill);
    }


    public boolean increaseAllowed(Skill skill) {
        return this.experienceGrid.increaseAllowed(skill);
    }

    public boolean decreaseAllowed(Skill skill) {
        return this.experienceGrid.decreaseAllowed(skill);
    }

    public int getAvailableExperience() {
        return this.experienceGrid.getAvailableExperience();
    }
}
