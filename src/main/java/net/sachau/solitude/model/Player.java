package net.sachau.solitude.model;

import net.sachau.solitude.item.Armor;
import net.sachau.solitude.item.Fist;
import net.sachau.solitude.item.Item;
import net.sachau.solitude.item.Weapon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements Serializable {

    public static int MAX_ACTIONS = 300;
    public static int MAX_HITS = 1000;

    private int turn = 1;
    private int actions = MAX_ACTIONS;
    private Map<ActionType, Integer> maxAdditionalActions = new HashMap<>();
    private Map<ActionType, Integer> additionalActions = new HashMap<>();

    private int ammo;
    private int y,x;

    private Weapon defaultWeapon = new Fist();

    // Skills
    private int weapon;
    private int computer;
    private int stealth;

    // hitpoints
    private int hits = MAX_HITS;
    private int mind;

    // Items
    private Item left;
    private Item right;
    private Item body;
    private List<Item> stash = new ArrayList<>();

    public Player() {
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public int getComputer() {
        return computer;
    }

    public void setComputer(int computer) {
        this.computer = computer;
    }

    public int getStealth() {
        return stealth;
    }

    public void setStealth(int stealth) {
        this.stealth = stealth;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getMind() {
        return mind;
    }

    public void setMind(int mind) {
        this.mind = mind;
    }

    public Item getLeft() {
        return left;
    }

    public void setLeft(Item left) {
        this.left = left;
    }

    public Item getRight() {
        return right;
    }

    public void setRight(Item right) {
        this.right = right;
    }

    public List<Item> getStash() {
        return stash;
    }

    public void setStash(List<Item> stash) {
        this.stash = stash;
    }

    public int getActions() {
        return actions;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }

    public boolean hasActions(ActionType actionType) {
        Integer action = additionalActions.get(actionType);
        if (action != null && action.intValue() > 0) {
            return true;
        } else {
            return actions > 0;
        }
    }

    public void useAction(ActionType actionType) {
        Integer action = additionalActions.get(actionType);
        if (action != null && action.intValue() > 0) {
            int remaining = Math.max(action -1, 0);
            additionalActions.put(actionType, remaining);
        } else {
            actions--;
        }
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public void nextTurn() {
        actions = MAX_ACTIONS;
        for (Map.Entry<ActionType, Integer> entry : maxAdditionalActions.entrySet()) {
            additionalActions.put(entry.getKey(), entry.getValue());
        }
        turn ++;

    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Item getBody() {
        return body;
    }

    public void setBody(Item body) {
        this.body = body;
    }

    public Armor getArmor() {
        if (body != null && body instanceof Armor) {
            return (Armor) body;
        }
        return null;
    }

    public void addStash(Item item) {
        this.getStash().add(item);
    }

    public Weapon getDefaultWeapon() {
        return defaultWeapon;
    }

    public void setDefaultWeapon(Weapon defaultWeapon) {
        this.defaultWeapon = defaultWeapon;
    }
}
