package net.sachau.solitude.item;

import net.sachau.solitude.Messages;
import net.sachau.solitude.gui.Icons;

public class Fist extends Weapon {

    public Fist() {
        super(Messages.get("item.pistol"), Icons.Name.FIST, 1,  0, false);
        addAllowLocation(Location.HAND);
    }
}
