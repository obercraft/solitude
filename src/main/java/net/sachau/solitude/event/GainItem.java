package net.sachau.solitude.event;

import net.sachau.solitude.Messages;
import net.sachau.solitude.card.EventCard;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.item.Item;

public class GainItem extends EventCard {

    private Item item;

    public GainItem(Item item) {
        super(Messages.get("event-gain-item", item.getName()));
    }

    @Override
    public void execute(GameEngine gameEngine) {
        gameEngine.getPlayer().addStash(item);
    }
}
