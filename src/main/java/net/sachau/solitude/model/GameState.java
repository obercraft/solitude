package net.sachau.solitude.model;

import net.sachau.solitude.missions.Mission;

import java.io.Serializable;

public class GameState implements Serializable {

    private Player player;
    private Mission mission;


    public GameState(Player player, Mission mission) {
        this.player = player;
        this.mission = mission;
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
}
