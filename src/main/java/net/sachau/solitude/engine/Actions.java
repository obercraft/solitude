package net.sachau.solitude.engine;

import net.sachau.solitude.card.ActionCard;
import net.sachau.solitude.enemy.Enemy;
import net.sachau.solitude.item.Weapon;
import net.sachau.solitude.mission.Mission;
import net.sachau.solitude.model.ActionType;
import net.sachau.solitude.model.Distance;
import net.sachau.solitude.model.Player;
import net.sachau.solitude.model.Skill;

@Component
public class Actions {

    private final Events events;

    @Autowired
    public Actions(Events events) {
        this.events = events;
    }

    private void sendError(String message) {
        events.send(new EventContainer(Event.ERROR, message));
    }

    public void attack(Mission mission, Player player, Enemy enemy) {
        if (!player.hasActions(ActionType.ATTACK)) {
            sendError("no more actions");
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
        Distance distance = mission.getMap().calculateDistance(player.getY(), player.getX(), enemy.getY(), enemy.getX());
        if (distance.isBlocked()) {
            sendError("blocked way");
            return;
        } else if (distance.getRooms() > weapon.getRange()) {
            sendError("too far away");
            return;
        } else if (weapon.isUsesAmmo() && player.getAmmo() <=0) {
            sendError("no ammo");
            return;
        } else {
            weapon.getDamage();
        }

        int skill = player.getSkill(Skill.ATTACK);
        player.useAction(ActionType.ATTACK);
        int result = 0;
        int ammo = 0;
        for (int i = 0; i < skill; i++) {
            ActionCard actionCard = mission.getActionCards().draw();
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
            sendError("missed");
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


}
