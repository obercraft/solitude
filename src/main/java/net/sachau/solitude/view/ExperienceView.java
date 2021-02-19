package net.sachau.solitude.view;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.sachau.solitude.Messages;
import net.sachau.solitude.engine.Autowired;
import net.sachau.solitude.engine.GameEngine;
import net.sachau.solitude.engine.View;
import net.sachau.solitude.experience.ExperienceManager;
import net.sachau.solitude.model.Attribute;

@View
public class ExperienceView extends VBox {

    private final GameEngine gameEngine;

    private final ExperienceManager experienceManager;

    private Text available = new Text();
    private Button done = new Button(Messages.get("game.done"));
    @Autowired
    public ExperienceView(GameEngine gameEngine, ExperienceManager experienceManager) {
        super();
        this.gameEngine = gameEngine;
        this.experienceManager = experienceManager;

        done.setOnMouseClicked(event -> {
            gameEngine.updateExperience(experienceManager.getExperienceGrid());
        });
    }

    public void update() {
        experienceManager.init();
        getChildren().clear();
        getChildren().add(available);
        for (Attribute attribute : Attribute.values()) {
            getChildren().add(new SkillRowView(available, attribute));
        }
        getChildren().add(done);

    }


    class SkillRowView extends HBox {

        Text skillName = new Text();
        Text skillValue = new Text();
        Button increase = new Button("+");
        Button decrease = new Button("-");
        private Attribute attribute;
        SkillRowView(Text available, Attribute attribute) {
            this.attribute = attribute;
            updateRow();
            getChildren().addAll(skillName, skillValue, increase, decrease);

            increase.setOnMouseClicked(event -> {
                event.consume();
                experienceManager.increase(this.attribute);
                updateRow();
            });

            decrease.setOnMouseClicked(event -> {
                event.consume();
                experienceManager.decrease(this.attribute);
                updateRow();
            });

        }

        private void updateRow() {
            skillName.setText(attribute.getName());
            skillValue.setText("" + experienceManager.getValue(attribute));
            //increase.setDisable(!experienceManager.increaseAllowed(skill));
            //decrease.setDisable(!experienceManager.decreaseAllowed(skill));
            available.setText("" + experienceManager.getAvailableExperience());

        }
    }
}


