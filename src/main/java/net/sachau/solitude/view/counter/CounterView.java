package net.sachau.solitude.view.counter;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import net.sachau.solitude.model.Counter;
import net.sachau.solitude.view.Icons;

abstract class CounterView extends StackPane {

    private static int size = 60;
    private static int panelHeight = 15;
    CounterPanel counterPanel;

    public CounterView(Counter counter) {
        super();
        this.setMinSize(size, size);
        this.setMaxSize(size, size);
        this.setPrefSize(size,size);
        this.counterPanel = new CounterPanel(size);

        ImageView imageView;
        if (counter == null) {
            imageView = new ImageView(Icons.get(Icons.Name.PLAYER));
        } else {
            imageView =new ImageView(Icons.get(counter.getIcon()));
        }
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        getStyleClass().add("counter-panel");
        getChildren().addAll(imageView, counterPanel);
        update(counter);

    }

    public  abstract void update(Counter counter);

//    public void update(Counter counter) {
//        this.enemy = counter;
//        int x = counter
//                .getX();
//        int y = counter
//                .getY();
//        relocate(MissionMapView.roomX(x), MissionMapView.roomY(y));
//        String value;
//        if (counter instanceof Enemy) {
//            Enemy enemy = (Enemy) counter;
//            if (!counter.isRevealed()) {
//                value = "?";
//            } else {
//                value = getEnemyStatString(counter);
//            }
//            if (counter.isAlerted()) {
//                value += " !";
//            }
//        } else {
//            value = getEnemyStatString(counter);
//        }
//        counterPanel.setText(value);
//    }


    class CounterPanel extends HBox {

        Text text = new Text();
        public CounterPanel(int width) {
            super();
            setMaxSize(width, panelHeight);
            setMinSize(width, panelHeight);
            text.setText("");
            getStyleClass().add("counter-panel-background");
            text.getStyleClass().add("counter-text");
            getChildren().add(text);
        }

        public void setText(String value) {
            this.text.setText(value);
        }
    }
}
