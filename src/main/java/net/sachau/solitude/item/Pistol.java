package net.sachau.solitude.item;

import net.sachau.solitude.Messages;

public class Pistol extends Weapon {

    public Pistol() {
        super(Messages.get("item.pistol"), 2,  1, true);
        addAllowLocation(Location.HAND);
    }
}
