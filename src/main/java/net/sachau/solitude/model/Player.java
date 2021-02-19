package net.sachau.solitude.model;

import net.sachau.solitude.view.Icons;
import net.sachau.solitude.item.Armor;
import net.sachau.solitude.item.Fist;
import net.sachau.solitude.item.Item;
import net.sachau.solitude.item.Weapon;

import java.io.Serializable;
import java.util.*;

public class Player implements Serializable, Counter {

    public static int MAX_ACTIONS = 300;
    public static int MAX_HITS = 1000;

    private int turn = 1;
    private int actions = MAX_ACTIONS;
    private Map<ActionType, Integer> maxAdditionalActions = new HashMap<>();
    private Map<ActionType, Integer> additionalActions = new HashMap<>();

    private int ammo;
    private int y,x;

    private Weapon defaultWeapon = new Fist();

    private Map<Attribute, Integer> attributes = new HashMap<>();
    private Map<Attribute, Integer> attributesBonus = new HashMap<>();

    // Items
    private Item hands;
    private Item feet;
    private Item body;
    private Set<Item> stash = new HashSet<>();

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

    public int getHits() {
        return getAttribute(Attribute.HITS);
    }

    public void setHits(int hits) {
        attributes.put(Attribute.HITS, hits);
    }

    public Item getHands() {
        return hands;
    }

    public void setHands(Item hands) {
        this.hands = hands;
    }

    public Item getFeet() {
        return feet;
    }

    public void setFeet(Item feet) {
        this.feet = feet;
    }

    public Set<Item> getStash() {
        return stash;
    }

    public void setStash(Set<Item> stash) {
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

    public Map<Attribute, Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<Attribute, Integer> attributes) {
        this.attributes = attributes;
    }
    public int getAttribute(Attribute attribute) {
        Integer skillValue = attributes.get(attribute);
        Integer aBonus = attributesBonus.get(attribute);
        int bonus = aBonus != null ? aBonus.intValue() : 0;
        return bonus + (skillValue != null ? skillValue.intValue() : 1);

    }

    public Map<Attribute, Integer> getAttributesBonus() {
        return attributesBonus;
    }

    public void setAttributesBonus(Map<Attribute, Integer> attributesBonus) {
        this.attributesBonus = attributesBonus;
    }

    public void addBonus(Attribute attribute, int value) {
        Integer currentBonus = attributesBonus.get(attribute);
        int bonus = value + (currentBonus != null ? currentBonus.intValue() : 0);
        attributesBonus.put(attribute, bonus);
    }

    @Override
    public Icons.Name getIcon() {
        return Icons.Name.PLAYER;
    }

    @Override
    public String getStatString() {
        return "Player";
    }
}
