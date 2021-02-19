package net.sachau.solitude.asset;

import net.sachau.solitude.Messages;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.view.Icons;
import net.sachau.solitude.model.Player;

public class Burning extends Asset {

    public Burning() {
        super(Messages.get("asset.burning"), Icons.Name.FIRE);
    }

    @Override
    public void use(GameEngine gameEngine) {

    }

    @Override
    public void executeEndOfTurn(GameEngine gameEngine) {
        Player player = gameEngine.getPlayer();
        if (player.getY() == getSpace().getY() && player.getX() == getSpace().getX()) {
            int remainingHits = Math.max(player.getHits() -1, 0);
            player.setHits(remainingHits);
            gameEngine.sendMessage("message.fire");
        }
    }
}
