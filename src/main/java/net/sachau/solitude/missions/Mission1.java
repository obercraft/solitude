package net.sachau.solitude.missions;

import net.sachau.solitude.ComponentManager;
import net.sachau.solitude.assets.Elevator;
import net.sachau.solitude.assets.Locker;
import net.sachau.solitude.assets.Nest;
import net.sachau.solitude.cards.Deck;
import net.sachau.solitude.cards.EventCard;
import net.sachau.solitude.cards.events.CreateEnemyType1;
import net.sachau.solitude.cards.events.Nothing;
import net.sachau.solitude.engine.Events;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.items.FirstAidKit;
import net.sachau.solitude.model.*;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Mission1 extends Mission implements Observer {

    private MissionMap map;
    private boolean goalReached = false;
    private Room startingPosition;

    private Deck<EventCard> eventCards = new Deck<>();

    public Mission1() {
        super();
        Events events = ComponentManager.getInstance()
                .getBean(Events.class);
        events.addObserver(this);
        setTurns(8);
    }

    @Override
    public void generateMap(GameEngine gameEngine) {

        MissionMapParseResult missionMapParseResult;
        try {
            missionMapParseResult = MissionParser.parseMission("1");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        int height = missionMapParseResult.getHeight();
        int width = missionMapParseResult.getWidth();

        map = new MissionMap(height, width);

        createEventDeck();

        int y = 0;
        for (String line : missionMapParseResult.getLines()) {
            for (int x = 0; x < line.length(); x ++) {
                char c = line.charAt(x);
                switch (c) {
                    case '_': {
                        Room room = new Room(y, x);
                        room.setBlank(true);
                        map.addSpace(room, y, x);
                        break;

                    }
                    case 'E': {
                        Room elevator = new Room(y, x);
                        elevator.addAsset(new Elevator());
                        map.addSpace(elevator, y, x);
                        break;
                    }
                    case 'S':
                    case '#': {
                        map.addSpace(new Room(y, x), y, x);
                        if (c == 'S') {
                            startingPosition = (Room) map.getSpace(y,x);
                            startingPosition.addAsset(new Nest());
                        } else {
                            map.getSpace(y,x).setEventCard(eventCards.draw());
                        }
                        break;
                    }
                    case '~':
                    case '|':
                    case '-': {
                        Door door = new Door(y, x, true);
                        if (c == '~') {
                            door.setBlank(true);
                        }
                        //Door door = new Door(y, x, !drawResult());
                        if (c == '-') {
                            door.setVertical(true);
                        }
                        map.addSpace(door, y, x);
                        break;

                    }

                }
            }
            y++;
        }

        /*
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

         */
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
        return startingPosition;
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

    private void createEventDeck() {
        for (int i = 1; i <= 10; i++) {
            eventCards.add(new CreateEnemyType1());
        }

        for (int i = 1; i <= 10; i++) {
            eventCards.add(new Nothing());
        }

        eventCards.shuffle();
    }
}
