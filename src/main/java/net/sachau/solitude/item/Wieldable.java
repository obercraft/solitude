package net.sachau.solitude.item;

import net.sachau.solitude.engine.GameEngine;

public interface Wieldable {

    void wield(GameEngine gameEngine);
    void unwield(GameEngine gameEngine);
}
