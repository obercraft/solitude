package net.sachau.solitude.campaign;

import java.util.ArrayList;
import java.util.List;

public abstract class Campaign {

    private String name;
    private List<CampaignStep> steps;

    public Campaign(String name) {
        this.name = name;
        this.steps = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CampaignStep> getSteps() {
        return steps;
    }

    public void setSteps(List<CampaignStep> steps) {
        this.steps = steps;
    }

    public void addStep(CampaignStep campaignStep) {
        this.steps.add(campaignStep);
    }
}
