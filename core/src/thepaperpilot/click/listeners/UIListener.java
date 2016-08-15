package thepaperpilot.click.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import thepaperpilot.click.components.ActorComponent;
import thepaperpilot.click.util.Mappers;

public class UIListener implements EntityListener {
    private Stage ui;

    public UIListener(Stage ui) {
        this.ui = ui;
    }

    @Override
    public void entityAdded(Entity entity) {
        final ActorComponent ac = Mappers.actor.get(entity);

        if (Mappers.enemy.has(entity)) {
            ui.addAction(Actions.delay(1, Actions.run(new Runnable() {
                @Override
                public void run() {
                    ui.addActor(ac.actor);
                    if (!ac.front) ac.actor.toBack();
                }
            })));
        } else {
            ui.addActor(ac.actor);
            if (!ac.front) ac.actor.toBack();
        }
    }

    @Override
    public void entityRemoved(Entity entity) {
        ActorComponent ac = Mappers.actor.get(entity);

        ac.actor.remove();
    }
}
