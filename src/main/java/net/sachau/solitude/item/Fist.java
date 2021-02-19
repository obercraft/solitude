package net.sachau.solitude.item;

import net.sachau.solitude.Messages;
import net.sachau.solitude.view.Icons;

public class Fist extends Weapon {

    public Fist() {
        super(Messages.get("item.fist"), Icons.Name.FIST, 1,  0, false);
        addAllowLocation(Location.HANDS);
    }
}
