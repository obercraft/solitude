package net.sachau.solitude.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import net.sachau.solitude.enemy.Enemy;
import net.sachau.solitude.engine.*;
import net.sachau.solitude.model.*;
import net.sachau.solitude.view.counter.EnemyView;
import net.sachau.solitude.view.counter.PlayerView;
import net.sachau.solitude.view.space.DoorView;
import net.sachau.solitude.view.space.RoomView;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

@View
public class MissionMapView extends ScrollPane implements Observer {

    public static int roomWidth = 150;
    public static int roomHeight = 150;
    public static int corridorSize = 50;

    private final GameEngine gameEngine;

    private final PlayerView playerView;
    Map<Long, EnemyView> enemyViews = new ConcurrentHashMap<>();

    RoomView playerLocation;

    RoomView locations[][];
    DoorView doors[][];

    private AnchorPane mapArea = new AnchorPane();

    @Autowired
    public MissionMapView(GameEngine gameEngine, PlayerView playerView) {
        super();
        this.gameEngine = gameEngine;
        this.playerView = playerView;
        setContent(mapArea);
        this.getStyleClass().add("steel");
        mapArea.getStyleClass().add("steel");
        gameEngine.addObserver(this);
        update();
    }
    public void update() {
        Player player = gameEngine.getPlayer();
        if (player == null) {
            return;
        }

        int height = gameEngine.getMissionMap()
                .getHeight();
        int width = gameEngine.getMissionMap()
                .getWidth();
        locations = new RoomView[height][width];
        doors = new DoorView[height][width];

        double offsetTop = 0;

        for (int h = 0; h < height; h++) {
            double offsetLeft = 0;
            double offsetTopIncrease = 0;
            for (int w = 0; w < width; w++) {

                Space space = gameEngine.getMissionMap()
                        .getSpace(h, w);

                if (space != null) {
                    double maxHeight;
                    if (space instanceof Room) {
                        locations[h][w] = new RoomView(gameEngine, (Room) space);
                        mapArea.getChildren().add(locations[h][w]);
                        locations[h][w].relocate(offsetLeft, offsetTop);
                        offsetLeft += locations[h][w].getMaxWidth();
                        maxHeight = locations[h][w].getMaxHeight();

                    } else if (space instanceof Door) {
                        doors[h][w] = new DoorView(gameEngine, (Door) space);
                        mapArea.getChildren().add(doors[h][w]);
                        doors[h][w].relocate(offsetLeft, offsetTop);
                        offsetLeft += doors[h][w].getMaxWidth();
                        maxHeight = doors[h][w].getMaxHeight();

                    } else {
                        maxHeight = 0;
                    }
                    if (!space.isBlank()) {
                        offsetTopIncrease = Math.max(offsetTopIncrease, maxHeight);
                    }
                }
            }

            offsetTop += offsetTopIncrease;
        }


        playerLocation = locations[gameEngine.getMission().startingPosition().getY()][gameEngine.getMission().startingPosition().getX()];
        gameEngine.getPlayer().setY(gameEngine.getMission().startingPosition().getY());
        gameEngine.getPlayer().setX(gameEngine.getMission().startingPosition().getX());

        for (Map.Entry<Long, Enemy> enemy : gameEngine.getMission().getEnemies().entrySet()) {
            EnemyView enemyView = new EnemyView(enemy.getValue());
            ((RoomView)locations[enemy.getValue()
                    .getY()][enemy.getValue()
                    .getX()]).addContent(enemyView);
            enemyViews.put(enemy.getKey(), enemyView);
            enemyView.update(enemy.getValue());

        }

        updateEnemies();

        relocatePlayer(gameEngine.getPlayer());

        revealRooms();


    }

    private void updateEnemies() {
        int y = gameEngine.getPlayer()
                .getY();
        int x = gameEngine.getPlayer()
                .getX();
        for (Enemy enemy : gameEngine.getMission()
                .getEnemies()
                .values()) {
            if (enemy.getX() == x && enemy.getY() == y) {
                enemy.setRevealed(true);
            }
            EnemyView ev = enemyViews.get(enemy.getId());
            if (enemy.isCreated()) {
                ev = new EnemyView(enemy);
                enemyViews.put(enemy.getId(), ev);
                ((RoomView) locations[enemy.getY()][enemy.getX()]).addContent(ev);
                enemy.setCreated(false);
            } else if (enemy.hasMoved()) {
                ((RoomView)locations[enemy.getLastY()][enemy.getLastX()]).removeContent(ev);
                locations[enemy.getY()][enemy.getX()].addContent(ev);
            }
            enemyViews.get(enemy.getId())
                    .update(enemy);
        }
        //
//        for (EnemyView ev : enemyViews.values()) {
//            for (RoomView rv : roomViews) {
//                if (rv.getChildren().contains(ev)) {
//                    rv.getChildren().remove(ev);
//                }
//                if (rv.getX() == ev.getEnemy().getX() && rv.getY() == ev.getEnemy().getY()) {
//                    rv.getChildren().add(ev);
//                }
//            }
//        }
    }


    //
//    public HBox createRoom(int y, int x) {
//        HBox roomPane = new HBox();
//
//        roomPane.setMinSize(MissionMapView.roomWidth, MissionMapView.roomHeight);
//        roomPane.setMaxSize(MissionMapView.roomWidth, MissionMapView.roomHeight);
//        roomPane.setBorder(new Border(new BorderStroke(Color.BLACK,
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//
//
//        roomPane.setOnMouseClicked(event -> {
//            event.consume();
//        });
//
//        roomPane.setOnDragOver(new EventHandler<DragEvent>() {
//            @Override
//            public void handle(DragEvent event) {
//                if (event.getDragboard()
//                        .hasContent(PlayerView.playerDataFormat)) {
//                    event.acceptTransferModes(TransferMode.ANY);
//                }
//                event.consume();
//            }
//        });
//
//        roomPane.setOnDragDropped(new EventHandler<DragEvent>() {
//            @Override
//            public void handle(DragEvent event) {
//                Player player = gameEngine.getPlayer();
//                gameEngine.move(y - player.getY(), x - player.getX());
//                event.consume();
//            }
//        });
//        return roomPane;
//    }
//
//    public VBox createDoor() {
//        VBox door = new VBox();
//
//        door.setMinSize(corridorSize, corridorSize);
//        door.setMaxSize(corridorSize, corridorSize);
//        door.setBorder(new Border(new BorderStroke(Color.BLACK,
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//        door.setOnMouseClicked(event -> {
//        });
//        return door;
//    }
//
    @Override
    public void update(Observable o, Object arg) {
        switch (Events.getType(arg)) {
            case PLAYER_ATTACK_DONE: {
                Enemy enemy = Events.getData(Enemy.class, arg);
                enemyViews.get(enemy.getId())
                        .update(enemy);
                if (enemy.getHits() <= 0) {
                    EnemyView ev = enemyViews.remove(enemy.getId());
                    locations[enemy.getY()][enemy.getX()].removeContent(ev);
                    enemyViews.remove(enemy.getId());
                    gameEngine.getMission()
                            .getEnemies()
                            .remove(enemy.getId());
                }
                gameEngine.send(Event.PLAYER_UPDATE);
                return;
            }
            case PLAYER_OPEN_DOOR_DONE: {
                Door door = Events.getData(Door.class, arg);
                doors[door.getY()][door.getX()].update(door);
                gameEngine.send(Event.PLAYER_UPDATE);
                return;
            }

            case PLAYER_MOVE_DONE: {
                Player player = gameEngine.getPlayer();
                relocatePlayer(player);
                gameEngine.send(Event.PLAYER_UPDATE);
            }

            case PLAYER_UPDATE: {
                playerView.update(gameEngine.getPlayer());
                gameEngine.send(Event.STATUS_BAR_UPDATE);
                return;
            }
            case NEXT_TURN_GUI: {
                gameEngine.send(Event.PLAYER_UPDATE);
                updateEnemies();
                return;
            }

            case UPDATE_DOOR: {
                Door door = Events.getData(Door.class, arg);
                doors[door.getY()][door.getX()].update(door);
                gameEngine.send(Event.PLAYER_UPDATE);
                return;
            }
            case UPDATE_ENEMIES: {
                updateEnemies();
                return;
            }
        }
    }

    private void relocatePlayer(Player player) {

        playerLocation.removeContent(playerView);
        locations[player.getY()][player.getX()].addContent(playerView);
        playerLocation = locations[player.getY()][player.getX()];
        revealRooms();
    }

    private void revealRoom(int y, int x) {
        Space space = gameEngine.getMissionMap()
                .getSpace(y, x);
        if (space != null) {
            if (space instanceof Door) {
                doors[y][x].update((Door)space);
            } else if (space instanceof Room) {
                locations[y][x].update((Room)space);
            }
            space.setDiscovered(true);

        }
    }

    private void revealRooms() {
        int y = gameEngine.getPlayer()
                .getY();
        int x = gameEngine.getPlayer()
                .getX();


        for (int h = -1; h <= 1; h++) {
            for (int w = -1; w <= 1; w++) {
                if (Math.abs(h) == 1 && Math.abs(w) == 1) {
                    // ignore diagonal
                } else {
                    revealRoom(y + h, x + w);
                }
            }
        }
        updateEnemies();


    }

    public static double roomX(int x) {
        return x * roomWidth;
    }

    public static double roomY(int y) {
        return y * roomHeight;
    }

}
