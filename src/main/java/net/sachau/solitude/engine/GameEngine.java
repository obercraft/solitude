package net.sachau.solitude.engine;

import net.sachau.solitude.Logger;
import net.sachau.solitude.items.Weapon;
import net.sachau.solitude.model.*;

import java.util.Observable;
import java.util.Observer;

@Component
public class GameEngine implements Observer {

    private final Events events;

    private GameState gameState;

    @Autowired
    public GameEngine(Events events) {
        this.events = events;
        this.addObserver(this);
    }

    public void send(Event event) {
        events.send(event);
    }
    public void sendError(String message) {
        events.send(new EventContainer(Event.ERROR, message));
    }

    public void send(Event event, Object object) {
        events.send(new EventContainer(event, object));
    }


    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Player getPlayer() {
        return gameState.getPlayer();
    }

    public Mission getMission() {
        return gameState.getMission();
    }

    public MissionMap getMissionMap() {
        return gameState.getMission().getMap();
    }

    @Override
    public void update(Observable o, Object arg) {
        switch(Events.getType(arg)) {
            case PLAYER_DIED: {
                sendError("game over - player died");
                System.exit(0);
                return;
            }
            case PLAYER_MISSION_FAILED: {
                sendError("game over - mission failed");
                System.exit(0);
                return;
            }
            case PLAYER_WON: {
                sendError("game over - player won");
                System.exit(0);
                return;
            }
            case ERROR: {
                Logger.debug(Events.getData(String.class, arg));
                return;
            }
            case NEXT_TURN: {
                boolean goalReached = getMission()
                        .checkGoal();
                if (goalReached) {
                    send(Event.PLAYER_WON);
                    return;
                }
                getPlayer()
                        .nextTurn();
                if (getPlayer()
                        .getTurn() >= getMission()
                        .getTurns()) {
                    send(Event.PLAYER_MISSION_FAILED);
                    return;
                }


                enemyActivation();
                send(Event.NEXT_TURN_GUI);
                return;
            }

        }

    }

    public void addObserver(Observer object) {
        events.addObserver(object);
    }


    public void openDoor(Door targetDoor) {
        if (targetDoor == null) {
            sendError("there is no door");
            return;
        }
        Player player = getPlayer();
        if (!player.hasActions()) {
            sendError("no action left");
            return;
        }

        if (targetDoor.isLocked()) {
            sendError("door is locked");
            return;
        } else if (!targetDoor.isClosed()) {
            sendError("door is already open");
            return;
        } else {
            player.useAction();
            targetDoor.setClosed(false);
            send(Event.PLAYER_OPEN_DOOR_DONE, targetDoor);
        }
    }

    public void moveTo(Space space) {

        Player player = getPlayer();
        if (!player.hasActions()) {
            sendError("no actions left");
            return;
        }
        Distance distance = getMissionMap().calculateDistance(player.getY(), player.getX(), space.getY(), space.getX());
        if (distance.isDiagonal()) {
            sendError("diagonal not possible");
            return;
        } else if (distance.isBlocked()) {
            sendError("blocked way");
            return;

        } else if (distance.getRooms() > 1) {
            sendError("move only one room");
            return;
        } else {
            // ok moving is allowed

            // check for attacks by enemies
            for (Enemy enemy : getMission().getEnemies().values()) {
                if (enemy.getY() == player.getY() && enemy.getX() == player.getX()) {
                    sendError("OPP ATTACK!");
                    enemyAttacksPlayer(enemy);
                }
            }

            player.setX(space.getX());
            player.setY(space.getY());
            player.useAction();

            int stealth = player.getStealth() + 1;
            int stealthResult = getMission().drawResult(stealth);

            for (Enemy enemy : getMission().getEnemies().values()) {
                Distance d = getMissionMap().calculateDistance(player.getY(), player.getX(), enemy.getY(), enemy.getX());
                if (d.getRooms() == 0) {
                    enemy.setRevealed(true);
                    enemy.setAlerted(true);
                    enemyAttacksPlayer(enemy);
                } else if (d.getRooms() > stealthResult) {
                    enemy.setAlerted(true);
                }
            }


            send(Event.PLAYER_MOVE_DONE);
        }
    }

    private void enemyAttacksPlayer(Enemy enemy) {
        Player player = getPlayer();
        int attackSkill = 1 + enemy.getAttack();
        int result = getMission().drawResult(attackSkill);
        if (result > 0) {
            player.setHits(Math.max(0, player.getHits() - enemy.getDamage()));
            sendError("enemy hits for " + enemy.getDamage());
        } else {
            sendError("enemy misses");
        }
        if (player.getHits() <=0) {
            sendError("player died");
            send(Event.PLAYER_DIED);
        }
    }

    public boolean offsetCheckDoor(int yOffset, int xOffset) {

        if (yOffset == 0 && xOffset == 0) {
            return true;
        }
        if (yOffset != 0 && xOffset != 0) {
            sendError("diagonal not possible");
            return false;
        }
        if (Math.abs(yOffset) != 1 && Math.abs(xOffset) != 1) {
            sendError("door only 1 room away");
            return false;
        } else {
            return true;
        }
    }


    public boolean offsetCheck(int yOffset, int xOffset) {
        if (yOffset != 0 && xOffset != 0) {
            sendError("diagonal not possible");
            return false;
        }
        if (Math.abs(yOffset) != 1 && Math.abs(xOffset) != 1) {
            sendError("only 1 room move");
            return false;
        }
        Player player = getPlayer();
        MissionMap missionMap = getMissionMap();
        if (player.getX() + xOffset <0 || player.getX() + xOffset >= missionMap.getWidth() || player.getY() + yOffset <0 || player.getY() + yOffset >= missionMap.getHeight()) {
            sendError("map-border reached");
            return false;
        } else {
            return true;
        }
    }


    public void attack(Enemy enemy) {
        Player player = getPlayer();
        if (!player.hasActions()) {
            sendError("no more actions");
            return;
        }
        Weapon weapon;
        if (player.getRight() != null && player.getRight() instanceof Weapon) {
            weapon =(Weapon) player.getRight();
        } else if (player.getLeft() != null && player.getLeft() instanceof Weapon) {
            weapon =(Weapon) player.getLeft();
        } else {
            weapon = new Weapon("Fist", 1, 1, false) {

            };
        }
        Distance distance = getMissionMap().calculateDistance(player.getY(), player.getX(), enemy.getY(), enemy.getX());
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

        int skill = player.getWeapon() + 1;
        player.useAction();
        int result = 0;
        int ammo = 0;
        for (int i = 0; i < skill; i++) {
            ActionCard actionCard = getMission().getActionCards().draw();
            result += actionCard.getSuccesses();
            ammo += actionCard.getAmmo();
        }

        if (distance.getRooms() > 1) {
            result --;
        }

        if (!enemy.isRevealed()) {
            result --;
        }

        if (result <= 0) {
            sendError("missed");
            send(Event.PLAYER_ATTACK_DONE, enemy);
            return;
        }

        if (weapon.isUsesAmmo()) {
            int remainingAmmo = Math.max(0, player.getAmmo() - ammo);
            player.setAmmo( remainingAmmo);
        }

        int remainingHits = Math.max(0, enemy.getHits() - weapon.getDamage());
        enemy.setHits(remainingHits);

        send(Event.PLAYER_ATTACK_DONE, enemy);

    }

    public void use(Room room, Asset asset) {
        Player player = getPlayer();
        if (!player.hasActions()) {
            sendError("no more actions");
            return;
        }
        Distance distance = getMissionMap().calculateDistance(player.getY(), player.getX(), room.getY(), room.getX());
        if (distance.getRooms() > 0) {
            sendError("too far away");
            return;
        }
        sendError("used " + asset.getName());
        asset.use(this);

    }

    public void enemyActivation() {
        for (Enemy enemy : getMission().getEnemies().values()) {
            enemy.setLastX(enemy.getX());
            enemy.setLastY(enemy.getY());
            if (!enemy.isAlerted()) {
                continue;
            }
            Player player = getPlayer();
            Distance distance = getMissionMap().calculateDistance(player.getY(), player.getX(), enemy.getY(), enemy.getX());

            if (distance.getRooms() > 0) {

                // try to move close
                Room targetRoom = null;
                if (enemy.getY() == player.getY()) {
                   targetRoom = enemyMoveToRoomX(enemy, player.getX());
                } else if (enemy.getX() == player.getX()) {
                    targetRoom = enemyMoveToRoomY(enemy, player.getY());
                }
                if (targetRoom != null) {
                    enemy.setX(targetRoom.getX());
                    enemy.setY(targetRoom.getY());
                } else {
                    enemy.setRevealed(false);
                }
            }
            distance = getMissionMap().calculateDistance(player.getY(), player.getX(), enemy.getY(), enemy.getX());
            if (distance.getRooms() == 0) {
                enemyAttacksPlayer(enemy);
            }

        }
    }

    private Room enemyMoveToRoomY(Enemy enemy, int targetY) {
        if (enemy.getY() == targetY) {
            return null;
        }
        if (enemy.getY() < targetY) {
            for (int y = enemy.getY() +1; y <=targetY; y ++) {
                EnterResult enterResult = enterNextSpace(y, enemy.getX());
                if (enterResult.getRoom() != null) {
                    return enterResult.getRoom();
                } else if (enterResult.isDoorOpened()) {
                    return null;
                }
            }
        } else {
            for (int y = enemy.getY() -1; y >= targetY; y --) {
                EnterResult enterResult = enterNextSpace(y, enemy.getX());
                if (enterResult.getRoom() != null) {
                    return enterResult.getRoom();
                } else if (enterResult.isDoorOpened()) {
                    return null;
                }
            }
        }
        return null;

    }

    private Room enemyMoveToRoomX(Enemy enemy, int targetX) {
        if (enemy.getX() == targetX) {
            return null;
        }
        if (enemy.getX() < targetX) {
            for (int x = enemy.getX() +1; x <=targetX; x ++) {
                EnterResult enterResult = enterNextSpace(enemy.getY(),x );
                if (enterResult.getRoom() != null) {
                    return enterResult.getRoom();
                } else if (enterResult.isDoorOpened()) {
                    return null;
                }

            }
        } else {
            for (int x = enemy.getX() -1; x >= targetX; x --) {
                EnterResult enterResult = enterNextSpace(enemy.getY(),x );
                if (enterResult.getRoom() != null) {
                    return enterResult.getRoom();
                } else if (enterResult.isDoorOpened()) {
                    return null;
                }
            }
        }
        return null;

    }

    private EnterResult enterNextSpace(int y, int x) {
        Space space = getMissionMap().getSpace(y, x);
        if (space instanceof Room) {
            return new EnterResult(false, (Room) space);
        }
        if (space instanceof Door) {
            if (space.isClosed()) {
                space.setClosed(false);
                send(Event.UPDATE_DOOR, (Door) space);
                return new EnterResult(true, null);
            } else {
                return new EnterResult(false, null);
            }
        }
        return new EnterResult(false, null);
    }

    private class EnterResult {
        boolean doorOpened;
        Room room;

        public EnterResult(boolean doorOpened, Room room) {
            this.doorOpened = doorOpened;
            this.room = room;
        }

        public boolean isDoorOpened() {
            return doorOpened;
        }

        public Room getRoom() {
            return room;
        }
    }
}
