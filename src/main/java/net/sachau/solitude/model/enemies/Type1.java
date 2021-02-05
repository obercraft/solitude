package net.sachau.solitude.model.enemies;

import net.sachau.solitude.model.Enemy;

public class Type1 extends Enemy {

    public Type1(Integer y, Integer x) {
        super(y, x);
        this.setAttack(0);
        this.setDamage(1);
        this.setHits(2);
    }
}

