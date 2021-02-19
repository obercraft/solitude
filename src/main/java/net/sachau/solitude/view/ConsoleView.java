package net.sachau.solitude.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.sachau.solitude.engine.Autowired;
import net.sachau.solitude.engine.Events;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.engine.View;

import java.util.Observable;
import java.util.Observer;

@View
public class ConsoleView extends ScrollPane implements Observer {

    private final TextManager textManager;
    private final GameEngine gameEngine;
    private TextFlow textFlow = new TextFlow();

    @Autowired
    public ConsoleView(TextManager textManager, GameEngine gameEngine) {
        this.textManager = textManager;
        this.gameEngine = gameEngine;
        this.gameEngine.addObserver(this);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
        this.setContent(textFlow);

        ConsoleView pane = this;
        textFlow.getStyleClass().add("steel");
        this.getStyleClass().add("steel");
        textFlow.heightProperty()
                .addListener(
                        (observable, oldValue, newValue) -> {
                            textFlow.layout();
                            pane.setVvalue(1.0d);
                        }
                );
    }

    @Override
    public void update(Observable o, Object arg) {
        switch (Events.getType(arg)) {
            case MESSAGE:
            case ERROR:
                textFlow.getChildren()
                        .addAll(new Text(Events.getData(String.class, arg) + "\n"));
                return;
            case TEXT_NODE:
                textFlow.getChildren().addAll(textManager.getTextNode(Events.getData(String.class, arg)).writeln());
                return;
        }

    }
}
