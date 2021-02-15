package net.sachau.solitude.model;

import net.sachau.solitude.campaign.Campaign;
import net.sachau.solitude.mission.Mission;

import java.io.Serializable;

public class GameState implements Serializable {

    private Player player;
    private Mission mission;
    private Campaign campaign;


    public GameState(Player player, Mission mission, Campaign campaign) {
        this.player = player;
        this.mission = mission;
        this.campaign = campaign;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }
}
