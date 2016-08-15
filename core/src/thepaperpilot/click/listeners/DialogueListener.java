package thepaperpilot.click.listeners;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import thepaperpilot.click.Main;
import thepaperpilot.click.components.ActorComponent;
import thepaperpilot.click.components.DialogueComponent;
import thepaperpilot.click.systems.DialogueSystem;
import thepaperpilot.click.ui.ScrollText;
import thepaperpilot.click.util.Constants;
import thepaperpilot.click.util.Mappers;

public class DialogueListener implements EntityListener {

    private Stage stage;
    private Engine engine;

    public DialogueListener(Stage stage, Engine engine) {
        this.stage = stage;
        this.engine = engine;
    }

    @Override
    public void entityAdded(final Entity entity) {
        final DialogueComponent dc = Mappers.dialogue.get(entity);
        ActorComponent ac = Mappers.actor.get(entity);

        dc.leftFace = new Image();
        dc.rightFace = new Image();

        dc.messageLabel = new ScrollText();
        dc.messageLabel.setAlignment(Align.topLeft);
        dc.messageLabel.setWrap(true);
        dc.message = new Table(Main.skin);
        dc.message.top().left();
        dc.message.setBackground(Main.skin.getDrawable("default-round"));
        dc.message.pad(20);

        Table dialogue = new Table(Main.skin);
        dialogue.pad(8).setFillParent(true);
        dialogue.bottom().left().add(dc.leftFace).width(3 * Constants.FACE_SIZE).height(4 * Constants.FACE_SIZE).pad(4);
        dialogue.add(dc.message).expandX().fillX().height(Constants.DIALOGUE_SIZE);
        dialogue.add(dc.rightFace).width(3 * Constants.FACE_SIZE).height(4 * Constants.FACE_SIZE).pad(4);
        ac.actor = dialogue;
        ac.front = true;

        stage.setKeyboardFocus(ac.actor);
        engine.getSystem(DialogueSystem.class).next(entity, dc.start);
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
