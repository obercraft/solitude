package net.sachau.solitude.missions;

import net.sachau.solitude.ComponentManager;
import net.sachau.solitude.assets.Elevator;
import net.sachau.solitude.assets.Locker;
import net.sachau.solitude.engine.Events;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.items.FirstAidKit;
import net.sachau.solitude.model.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

public class Mission1 extends Mission implements Observer {

    private MissionMap map;
    private boolean goalReached = false;

    public Mission1() {
        super();
        Events events = ComponentManager.getInstance()
                .getBean(Events.class);
        events.addObserver(this);
        setTurns(8);
    }

    @Override
    public void generateMap(GameEngine gameEngine) {
        int height = 3*2;
        int width = 5*2;
        map = new MissionMap(height, width);

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if ((h % 2) ==0) {

                    if (w % 2 == 0) {
                        map.addSpace(new Room(h, w), h, w);
                    } else {
                        map.addSpace(new Door(h, w, !drawResult()), h, w);
                    }

                    // map.addSpace(new Door(h, w), h, w);
                } else {
                    if (w % 2 == 1) {
                        Room room = new Room(h, w);
                        room.setBlank(true);
                        map.addSpace(room, h, w);
                    } else {
                        Door door = new Door(h, w, !drawResult());
                        door.setVertical(true);
                        map.addSpace(door, h, w);
                    }
                }

                {
                    Space space = map.getSpace(h, w);
                    boolean enemyPlacementAllowed = space instanceof Room && !space.isBlank();

                    int amount = drawResult(1);
                    if (h != 0 && w != 0 &&  enemyPlacementAllowed && amount > 0) {
                        for (int i = 0; i< amount; i++) {
                            EnemyCard card = getEnemyCards().draw();
                            Object[] parameters = new Object[2];
                            parameters[0] = h;
                            parameters[1] = w;
                            try {
                                Enemy enemy = card.getEnemyType()
                                        .getDeclaredConstructor(Integer.class, Integer.class)
                                        .newInstance(h, w);
                                //Enemy enemy = new Enemy(h, w, 2, 1);
                                getEnemies().put(enemy.getId(), enemy);
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        }
        Room elevator = (Room) map.getSpace(height/2 -1, width -2);
        elevator.addAsset(new Elevator());
        startingPosition().addProperty(new Locker());
        getItemChits().add(new FirstAidKit());
        //startingPosition().addProperty(Asset.Type.LOCKER);
        this.setMap(map);
    }

    @Override
    public boolean checkGoal() {
        return goalReached;
    }

    @Override
    public Room startingPosition() {
        return (Room) map.getSpace(0,0);
    }

    @Override
    public void update(Observable o, Object arg) {
        switch (Events.getType(arg)) {
            case PLAYER_USED:
                if (Events.getData(arg) instanceof Elevator) {
                    goalReached = true;
                }
                return;
        }
    }
}
