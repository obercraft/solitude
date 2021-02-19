package net.sachau.solitude.item;

import net.sachau.solitude.Messages;
import net.sachau.solitude.view.Icons;

public class Pistol extends Weapon {

    public Pistol() {
        super(Messages.get("item.pistol"), Icons.Name.PISTOL, 2,  1, true);
        addAllowLocation(Location.HANDS);
    }
}
