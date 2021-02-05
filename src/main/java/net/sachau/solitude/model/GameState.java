package net.sachau.solitude.model;

import net.sachau.solitude.model.Mission;
import net.sachau.solitude.model.MissionMap;
import net.sachau.solitude.model.Player;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

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
