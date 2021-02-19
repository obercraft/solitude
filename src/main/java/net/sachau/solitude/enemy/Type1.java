package net.sachau.solitude.enemy;

import net.sachau.solitude.view.Icons;

public class Type1 extends Enemy {

    public Type1(Integer y, Integer x) {
        super("Type 1", Icons.Name.TYPE1, y, x);
        this.setAttack(1);
        this.setDamage(1);
        this.setHits(2);
    }

    @Override
    public String getStatString() {
        return this.getAttack() +"-"+ this.getDamage() +"-"+getHits() + (isAlerted() ? " !" : "");
    }
}

