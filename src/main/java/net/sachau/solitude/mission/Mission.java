package net.sachau.solitude.mission;

import net.sachau.solitude.card.ActionCard;
import net.sachau.solitude.card.Deck;
import net.sachau.solitude.enemy.Enemy;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.item.Item;
import net.sachau.solitude.model.Chits;
import net.sachau.solitude.model.Room;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Mission implements Serializable {

    private String name;
    private MissionMap map;
    private Map<Long, Enemy> enemies = new ConcurrentHashMap<>();
    private int turns;

    private Deck<ActionCard> actionCards = new Deck<>();
    private Chits<Item> itemChits = new Chits<>();

    public Mission(String name) {
        this.name = name;
        // create action cards
        {

            // re-shuffle card
            actionCards.add(new ActionCard(-1));

            for (int i = 1; i <= 4; i++) {
                actionCards.add(new ActionCard(0, false, true));
            }


            for (int i = 1; i <= 2; i++) {
                actionCards.add(new ActionCard(2));
            }
            for (int i = 1; i <= 2; i++) {
                actionCards.add(new ActionCard(2, true, false));
            }

            for (int i = 1; i <= 20; i++) {
                actionCards.add(new ActionCard(1));
            }

            for (int i = 1; i <= 22; i++) {
                actionCards.add(new ActionCard(0));
            }
            int index = 0;
            for (ActionCard actionCard : actionCards.getDrawPile()) {
                for (int i = 1; i <= 10; i++) {
                    actionCard.getRandomNumber()
                            .add(index % i + 1);
                }
                index++;
            }
            actionCards.shuffle();
        }

    }

    public MissionMap getMap() {
        return map;
    }

    public void setMap(MissionMap map) {
        this.map = map;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public abstract void generateMap(GameEngine gameEngine);

    public abstract boolean checkGoal();

    public abstract Room startingPosition();

    public Map<Long, Enemy> getEnemies() {
        return enemies;
    }

    public Deck<ActionCard> getActionCards() {
        return actionCards;
    }

    public boolean drawResult() {
        ActionCard card = actionCards.draw();
        return drawResult(1) > 0;
    }

    public int drawResult(int amount) {
        int result = 0;
        for (int i = 0; i < amount; i++) {
            ActionCard card = actionCards.draw();
            while (card.getSuccesses() == -1) {
                actionCards.shuffle();
                card = actionCards.draw();
            }
            result += card.getSuccesses();
        }
        return result;
    }

    public Chits<Item> getItemChits() {
        return itemChits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
