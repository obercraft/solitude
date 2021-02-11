package net.sachau.solitude.campaign;

import net.sachau.solitude.mission.Mission1;

public class SolitudeCampaign extends Campaign {

    public SolitudeCampaign() {
        super("campaigns.campaign1");


        addStep(new CampaignStep(new Mission1()));
    }
}
