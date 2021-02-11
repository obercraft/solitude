package net.sachau.solitude.card;

import net.sachau.solitude.Messages;

import java.util.ArrayList;
import java.util.List;

public class ActionCard extends Card {

    private int successes;
    private boolean jam;
    private boolean momentum;
    private List<Integer> randomNumber = new ArrayList<>();

    public ActionCard(int successes) {
        super(Messages.get("card.action"));
        this.successes = successes;
    }

    public ActionCard(int successes, boolean jam, boolean momentum) {
        super(Messages.get("card.action"));
        this.successes = successes;
        this.jam = jam;
        this.momentum = momentum;
    }

    public int getSuccesses() {
        return successes;
    }

    public void setSuccesses(int successes) {
        this.successes = successes;
    }

    public List<Integer> getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(List<Integer> randomNumber) {
        this.randomNumber = randomNumber;
    }

    public boolean isJam() {
        return jam;
    }

    public void setJam(boolean jam) {
        this.jam = jam;
    }

    public boolean isMomentum() {
        return momentum;
    }

    public void setMomentum(boolean momentum) {
        this.momentum = momentum;
    }

    @Override
    public String toString() {
        return "ActionCard{" +
                "successes=" + successes +
                ", jam=" + jam +
                ", momentum=" + momentum +
                ", randomNumber=" + randomNumber +
                '}';
    }
}
