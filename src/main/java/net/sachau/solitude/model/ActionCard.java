package net.sachau.solitude.model;

import java.util.ArrayList;
import java.util.List;

public class ActionCard extends Card {

    private int successes;
    private int ammo;
    private int jam;
    private List<Integer> randomNumber = new ArrayList<>();

    public ActionCard(int successes) {
        this.successes = successes;
    }

    public ActionCard(int successes, int ammo) {
        this.successes = successes;
        this.ammo = ammo;
    }

    public ActionCard(int successes, int ammo, int jam) {
        this.successes = successes;
        this.ammo = ammo;
        this.jam = jam;
    }

    public int getSuccesses() {
        return successes;
    }

    public void setSuccesses(int successes) {
        this.successes = successes;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getJam() {
        return jam;
    }

    public void setJam(int jam) {
        this.jam = jam;
    }

    public List<Integer> getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(List<Integer> randomNumber) {
        this.randomNumber = randomNumber;
    }

    @Override
    public String toString() {
        return "ActionCard{" +
                "successes=" + successes +
                ", ammo=" + ammo +
                ", jam=" + jam +
                ", randomNumber=" + randomNumber +
                '}';
    }
}
