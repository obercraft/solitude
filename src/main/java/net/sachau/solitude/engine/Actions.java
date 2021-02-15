package net.sachau.solitude.engine;

import net.sachau.solitude.asset.Asset;
import net.sachau.solitude.card.ActionCard;
import net.sachau.solitude.card.EventCard;
import net.sachau.solitude.enemy.Enemy;
import net.sachau.solitude.item.Armor;
import net.sachau.solitude.item.Weapon;
import net.sachau.solitude.mission.Mission;
import net.sachau.solitude.model.*;

public class Actions {

    private final Events events;
    private final GameEngine gameEngine;

    @Autowired
    public Actions(GameEngine gameEngine, Events events) {
        this.events = events;
        this.gameEngine = gameEngine;
    }

    public void moveTo(Space space) {

        Player player = gameEngine.getPlayer();
        if (!player.hasActions(ActionType.MOVE)) {
            gameEngine.sendError("no actions left");
            return;
        }
        Distance distance = gameEngine.getMissionMap().calculateDistance(player.getY(), player.getX(), space.getY(), space.getX());
        if (distance.isDiagonal()) {
            gameEngine.sendError("diagonal not possible");
            return;
        } else if (distance.isBlocked()) {
            gameEngine.sendError("blocked way");
            return;

        } else if (distance.getRooms() > 1) {
            gameEngine.sendError("move only one room");
            return;
        } else {
            // ok moving is allowed

            // check for attacks by enemies
            for (Enemy enemy : gameEngine.getMission().getEnemies().values()) {
                if (enemy.getY() == player.getY() && enemy.getX() == player.getX()) {
                    gameEngine.sendError("OPP ATTACK!");
                    enemyAttacksPlayer(enemy);
                }
            }

            player.setX(space.getX());
            player.setY(space.getY());
            player.useAction(ActionType.MOVE);

            if (space.getEventCard() != null) {
                EventCard eventCard = space.getEventCard();
                eventCard.execute(gameEngine);
                space.setEventCard(null);
            }

            int stealth = player.getAttribute(Attribute.STEALTH);
            int stealthResult = gameEngine.getMission().drawResult(stealth);

            for (Enemy enemy : gameEngine.getMission().getEnemies().values()) {
                Distance d = gameEngine.getMissionMap().calculateDistance(player.getY(), player.getX(), enemy.getY(), enemy.getX());
                if (d.getRooms() == 0) {
                    enemy.setRevealed(true);
                    enemy.setAlerted(true);
                    enemyAttacksPlayer(enemy);
                } else if (d.getRooms() > stealthResult) {
                    enemy.setAlerted(true);
                }
            }


            gameEngine.send(Event.PLAYER_MOVE_DONE);
        }
    }

    public void attack(Enemy enemy) {
        Player player = gameEngine.getPlayer();
        if (!player.hasActions(ActionType.ATTACK)) {
            gameEngine.sendError("no more actions");
            return;
        }
        Weapon weapon;
        if (player.getRight() != null && player.getRight() instanceof Weapon) {
            weapon =(Weapon) player.getRight();
        } else if (player.getLeft() != null && player.getLeft() instanceof Weapon) {
            weapon =(Weapon) player.getLeft();
        } else {
            weapon = player.getDefaultWeapon();
        }
        Distance distance = gameEngine.getMissionMap().calculateDistance(player.getY(), player.getX(), enemy.getY(), enemy.getX());
        if (distance.isBlocked()) {
            gameEngine.sendError("blocked way");
            return;
        } else if (distance.getRooms() > weapon.getRange()) {
            gameEngine.sendError("too far away");
            return;
        } else if (weapon.isUsesAmmo() && player.getAmmo() <=0) {
            gameEngine.sendError("no ammo");
            return;
        } else {
            weapon.getDamage();
        }

        int skill = player.getAttribute(Attribute.ATTACK);
        player.useAction(ActionType.ATTACK);
        int result = 0;
        int ammo = 0;
        for (int i = 0; i < skill; i++) {
            ActionCard actionCard = gameEngine.getMission().getActionCards().draw();
            result += actionCard.getSuccesses();
            // ammo += actionCard.getAmmo();
        }

        if (distance.getRooms() > 1) {
            result --;
        }

        if (!enemy.isRevealed()) {
            result --;
        }

        if (result <= 0) {
            gameEngine.sendError("missed");
            events.send(new EventContainer(Event.PLAYER_ATTACK_DONE, enemy));
            return;
        }

        if (weapon.isUsesAmmo()) {
            int remainingAmmo = Math.max(0, player.getAmmo() - ammo);
            player.setAmmo( remainingAmmo);
        }

        int remainingHits = Math.max(0, enemy.getHits() - weapon.getDamage());
        enemy.setHits(remainingHits);

        events.send(new EventContainer(Event.PLAYER_ATTACK_DONE, enemy));

    }

    public void use(Room room, Asset asset) {
        Player player = gameEngine.getPlayer();
        if (!player.hasActions(ActionType.USE)) {
            gameEngine.sendError("no more actions");
            return;
        }
        Distance distance = gameEngine.getMissionMap().calculateDistance(player.getY(), player.getX(), room.getY(), room.getX());
        if (distance.getRooms() > 0) {
            gameEngine.sendError("too far away");
            return;
        }
        gameEngine.sendError("used " + asset.getName());
        asset.use(gameEngine);

    }


    void enemyAttacksPlayer(Enemy enemy) {
        Player player = gameEngine.getPlayer();
        int attackSkill = 1 + enemy.getAttack();
        int result = gameEngine.getMission().drawResult(attackSkill);
        if (result > 0) {
            int damage = enemy.getDamage();
            Armor armor = player.getArmor();
            if (armor != null) {

            }
            player.setHits(Math.max(0, player.getHits() - damage));
            gameEngine.sendError("enemy hits for " + enemy.getDamage());
        } else {
            gameEngine.sendError("enemy misses");
        }
        if (player.getHits() <=0) {
            gameEngine.sendError("player died");
            gameEngine.send(Event.PLAYER_DIED);
        }
    }

}
