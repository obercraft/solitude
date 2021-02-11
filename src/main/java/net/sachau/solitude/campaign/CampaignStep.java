package net.sachau.solitude.campaign;

import net.sachau.solitude.mission.Mission;

import java.util.ArrayList;
import java.util.List;

public class CampaignStep {

    private List<Mission> availableMissions = new ArrayList<>();
    private List<Mission> finishedMissions = new ArrayList<>();

    public CampaignStep(Mission ... missions) {
        for (Mission mission : missions) {
            this.availableMissions.add(mission);
        }
    }
}
