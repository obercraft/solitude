package net.sachau.solitude.engine;

import net.sachau.solitude.Logger;
import net.sachau.solitude.Messages;
import net.sachau.solitude.asset.Asset;
import net.sachau.solitude.campaign.Campaign;
import net.sachau.solitude.campaign.SolitudeCampaign;
import net.sachau.solitude.card.ActionCard;
import net.sachau.solitude.card.EventCard;
import net.sachau.solitude.enemy.Enemy;
import net.sachau.solitude.enemy.EnemyFactory;
import net.sachau.solitude.experience.ExperienceGrid;
import net.sachau.solitude.item.*;
import net.sachau.solitude.mission.Mission;
import net.sachau.solitude.mission.Mission1;
import net.sachau.solitude.mission.MissionMap;
import net.sachau.solitude.model.*;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

@Component
public class GameEngine implements Observer {

    private final Events events;

    private final Actions actions;

    private GameState gameState;



    @Autowired
    public GameEngine(Events events) {
        this.events = events;
        this.actions = new Actions(this);
        this.addObserver(this);
    }

    public void send(Event event) {
        events.send(event);
    }
    public void sendError(String message) {
        events.send(new EventContainer(Event.ERROR, message));
    }

    public void sendMessage(String message) {
        events.send(new EventContainer(Event.MESSAGE, Messages.get(message)));
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
        if (gameState == null) {
            return null;
        }
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

                if (getPlayer().getHits() <=0) {
                    send(Event.PLAYER_DIED);
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


                spaceActivation();
                enemyActivation();
                send(Event.NEXT_TURN_GUI);
                return;
            }

        }

    }

    private void spaceActivation() {
        MissionMap map = getMissionMap();
        int height = map.getHeight();
        int width = map.getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Space space = map.getSpace(y,x);
                if (space instanceof Room && space.isDiscovered()) {
                    Set<Asset> assets = ((Room) space).getAssets();
                    if (assets != null) {
                        for (Asset asset : assets) {
                            asset.executeEndOfTurn(this);
                        }
                    }
                }
            }
        }
    }

    public void addObserver(Observer object) {
        events.addObserver(object);
    }


    public void openDoor(Door targetDoor) {
        actions.openDoor(targetDoor);
    }

    public void use(Room room, Asset asset) {
        actions.use(room, asset);
    }

    public void moveTo(Space room) {
        actions.moveTo(room);
    }


    public void attack(Enemy enemy) {
        actions.attack(enemy);
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
                actions.enemyAttacksPlayer(enemy);
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

    public void addEnemy(Enemy.Type type) {
        Enemy enemy = EnemyFactory.create(type, getPlayer().getY(), getPlayer().getX());
        getMission().getEnemies().put(enemy.getId(), enemy);
    }

    public void handleFire() {
    }

    public void moveItem(Item sourceItem, Item targetItem, Item.Location location) {
        if (sourceItem == null) {
            return;
        } else if (sourceItem.equals(targetItem)) {
            return;
        }
        switch(location) {
            case BODY: {
                getPlayer().setBody(sourceItem);
                if (sourceItem instanceof Wieldable) {
                    ((Wieldable) sourceItem).wield(this);
                }
                if (targetItem != null) {
                    getPlayer().addStash(targetItem);
                    if (sourceItem instanceof Wieldable) {
                        ((Wieldable) targetItem).unwield(this);
                    }
                }
                getPlayer().getStash().remove(sourceItem);
                send(Event.UPDATE_EQUIPMENT);
                return;
            }
            case HANDS: {
                getPlayer().setHands(sourceItem);
                if (sourceItem instanceof Wieldable) {
                    ((Wieldable) sourceItem).wield(this);
                }
                if (targetItem != null) {
                    getPlayer().addStash(targetItem);
                    if (sourceItem instanceof Wieldable) {
                        ((Wieldable) targetItem).unwield(this);
                    }

                }
                getPlayer().getStash().remove(sourceItem);
                send(Event.UPDATE_EQUIPMENT);
                return;
            }
            case FEET: {
                getPlayer().setFeet(sourceItem);
                if (sourceItem instanceof Wieldable) {
                    ((Wieldable) sourceItem).wield(this);
                }
                if (targetItem != null) {
                    getPlayer().addStash(targetItem);
                    if (sourceItem instanceof Wieldable) {
                        ((Wieldable) targetItem).unwield(this);
                    }

                }
                getPlayer().getStash().remove(sourceItem);
                send(Event.UPDATE_EQUIPMENT);
                return;
            }

            default:
                break;
        }



    }

    public void startNewGame() {
                Player player = new Player();
        player.setHits(Player.MAX_HITS);
        player.setAmmo(2);
        player.addStash(new KevlarVest());
        player.addStash(new Pistol());
        Campaign solitudeCampaign  = new SolitudeCampaign();
        Mission mission1 = new Mission1();
        mission1.generateMap(this);
        mission1.getActionCards().shuffle();
        GameState gameState = new GameState(player, mission1, solitudeCampaign);
        setGameState(gameState);
    }

    public void updateExperience(ExperienceGrid experienceGrid) {
        getPlayer().setAttributes(experienceGrid.getIncreasedAttributes());
        send(Event.START_MISSION);
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
