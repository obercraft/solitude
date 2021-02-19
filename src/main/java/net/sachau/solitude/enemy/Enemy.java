package net.sachau.solitude.enemy;

import net.sachau.solitude.engine.Id;
import net.sachau.solitude.view.Icons;
import net.sachau.solitude.model.Counter;

public abstract class Enemy implements Counter {

    public enum Type {
        TYPE1,
    }

    private String name;
    private Icons.Name icon;

    private long id = Id.get();
    private int y, x, lastY, lastX;
    private boolean alerted;
    private int hits, damage, attack;
    private boolean revealed;
    private boolean created;


    public Enemy(String name, Icons.Name icon, Integer y, Integer x) {
        this.y = y;
        this.x = x;
        this.lastX = x;
        this.lastY = y;
        this.name = name;
        this.icon = icon;
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

    public boolean isAlerted() {
        return alerted;
    }

    public void setAlerted(boolean alerted) {
        this.alerted = alerted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public int getLastY() {
        return lastY;
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
    }

    public int getLastX() {
        return lastX;
    }

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public boolean hasMoved() {
        return this.lastX != this.x || this.lastY != this.y;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icons.Name getIcon() {
        return icon;
    }

    public void setIcon(Icons.Name icon) {
        this.icon = icon;
    }
}
