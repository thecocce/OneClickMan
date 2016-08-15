package thepaperpilot.click.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import thepaperpilot.click.Main;
import thepaperpilot.click.components.ActorComponent;
import thepaperpilot.click.components.DialogueComponent;
import thepaperpilot.click.components.EnemyComponent;
import thepaperpilot.click.listeners.DialogueListener;
import thepaperpilot.click.listeners.UIListener;
import thepaperpilot.click.systems.DialogueSystem;
import thepaperpilot.click.systems.EnemySystem;
import thepaperpilot.click.systems.Render3DSystem;
import thepaperpilot.click.systems.RenderStageSystem;
import thepaperpilot.click.util.Constants;
import thepaperpilot.click.util.Mappers;

import java.util.ArrayList;

public class GameScreen implements Screen {
    public final Stage stage;
    public final Engine engine;

    public GameScreen() {
        /* Create Stuff */
        stage = new Stage(new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT));
        engine = new Engine();

        /* Add Listeners to Engine */
        engine.addEntityListener(Family.all(ActorComponent.class, DialogueComponent.class).get(), 10, new DialogueListener(stage, engine));
        engine.addEntityListener(Family.all(ActorComponent.class).get(), 11, new UIListener(stage));

        /* Add Systems to Engine */
        engine.addSystem(new DialogueSystem()); // priority 5
        engine.addSystem(new RenderStageSystem(stage)); //priority 10
        engine.addSystem(new Render3DSystem()); // priority 9
        engine.addSystem(new EnemySystem(stage));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.getRoot().getColor().a = 0;
        stage.addAction(Actions.fadeIn(Constants.TRANSITION_TIME));
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
