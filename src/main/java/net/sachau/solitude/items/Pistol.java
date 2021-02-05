package net.sachau.solitude.items;

public class Pistol extends Weapon {

    public Pistol() {
        super("Pistol", 2,  1, true);
        addAllowLocation(Location.HAND);
    }
}
