package net.sachau.solitude.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.model.Room;
import net.sachau.solitude.assets.Asset;

public class AssetNode extends StackPane {

    private final GameEngine gameEngine;

    private static int size = 40;
    TextFlow text = new TextFlow();
    Tooltip toolTip = new Tooltip();
    Asset asset;

    public AssetNode(Room room, GameEngine gameEngine, Asset asset) {
        super();
        this.gameEngine = gameEngine;
        this.setHeight(size);
        this.setWidth(size);
        this.setMinSize(size, size);
        this.setMaxSize(size, size);
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        ImageView imageView = new ImageView(Icons.get(asset.getIcon()));
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        Tooltip.install(this, toolTip);
        getChildren().addAll(imageView, text);
        update(asset);

        setOnMouseClicked(event -> {
            event.consume();
            gameEngine.use(room, asset);
        });
    }

    public void update(Asset asset) {
        this.asset = asset;
        toolTip.setText(asset.getName());
        text.getChildren().clear();
    }

}
