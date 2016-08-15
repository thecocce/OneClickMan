package thepaperpilot.click.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import thepaperpilot.click.Main;
import thepaperpilot.click.components.ActorComponent;
import thepaperpilot.click.components.DialogueComponent;
import thepaperpilot.click.components.EnemyComponent;
import thepaperpilot.click.listeners.EnemyListener;
import thepaperpilot.click.util.Constants;
import thepaperpilot.click.util.Mappers;

import java.util.ArrayList;
import java.util.List;

public class EnemySystem extends EntitySystem {

    public Stage stage;
    public List<Entity> enemies;

    public EnemySystem(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(Family.all(EnemyComponent.class).get(), 9, new EnemyListener(this));

        String[] enemyStarts = new String[]{
                "pogo-1",
                "bug-1",
                "frog-1",
                "shell-1",
                "shark-1",
                "orca-1",
                "mann-1",
                "agor-1",
                "kronoz-1",
                "lord-1",
                "end-1"
        };
        String[] enemyModels = new String[]{
                "pogo",
                "bug",
                "frog",
                "shell",
                "shark",
                "orca",
                "mann",
                "agor",
                "kronoz",
                "lord",
                "end"
        };
        enemies = new ArrayList<Entity>();
        for (int i = 0; i < enemyStarts.length; i++) {
            enemies.add(getEnemy(enemyStarts[i], enemyModels[i]));
        }
        DialogueComponent dc = DialogueComponent.read("dialogues/enemy.json");
        dc.start = "pogo-1";
        dc.events.put("end", new Runnable() {
            @Override
            public void run() {
                Label label = new Label("Thanks for playing!", Main.skin, "large");
                Table table = new Table(Main.skin);
                table.setFillParent(true);
                table.bottom().pad(20).add(label).expandX().fill();
                stage.addActor(table);
                getEngine().getSystem(Render3DSystem.class).setOverviewPath();
                getEngine().getSystem(Render3DSystem.class).over = true;
            }
        });
        enemies.get(0).add(dc);
        engine.addEntity(enemies.get(0));
        Mappers.enemy.get(enemies.get(9)).invincible = true;

        final Image white = new Image(Main.getTexture("white"));
        white.setSize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        white.setColor(1, 1, 1, 0);
        stage.addActor(white);
        final Image black = new Image(Main.getTexture("black"));
        black.setSize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        black.setColor(1, 1, 1, 0);
        stage.addActor(black);
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (stage.getRoot().hasActions()) return false;
                update();
                return true;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (stage.getRoot().hasActions()) return false;
                update();
                return true;
            }

            void update() {
                getEngine().getSystem(Render3DSystem.class).setOverviewPath();

                for (Entity entity : getEngine().getEntitiesFor(Family.all(DialogueComponent.class).get()))
                    Mappers.actor.get(entity).actor.setVisible(false);

                if (Mappers.enemy.get(getEngine().getEntitiesFor(Family.all(EnemyComponent.class).get()).first()).invincible) {
                    stage.addAction(Actions.sequence(Actions.delay(1, Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            white.addAction(Actions.sequence(Actions.fadeIn(.25f, Interpolation.pow2In), Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    stage.getRoot().clearListeners();
                                    getEngine().removeEntity(getEngine().getEntitiesFor(Family.all(EnemyComponent.class).get()).first());
                                }
                            }), Actions.fadeOut(.5f, Interpolation.pow2Out)));
                        }
                    }))));
                } else {
                    stage.addAction(Actions.sequence(Actions.delay(1, Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            white.addAction(Actions.sequence(Actions.fadeIn(.25f, Interpolation.pow2In), Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    getEngine().getSystem(Render3DSystem.class).enemyInstance = null;
                                }
                            }), Actions.fadeOut(.5f, Interpolation.pow2Out)));
                        }
                    })), Actions.delay(2, Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            black.addAction(Actions.sequence(Actions.fadeIn(.5f), Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    getEngine().removeEntity(getEngine().getEntitiesFor(Family.all(EnemyComponent.class).get()).first());
                                }
                            }), Actions.fadeOut(.5f)));
                        }
                    }))));
                }
            }
        });
    }

    private Entity getEnemy(String enemy, String model) {
        Entity entity = new Entity();
        entity.add(new EnemyComponent(enemy, Render3DSystem.FlatObjectFactory.create(Main.getTexture(model), 1/200f)));
        entity.add(new ActorComponent());
        return entity;
    }
}
