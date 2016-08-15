package thepaperpilot.click.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import thepaperpilot.click.Main;
import thepaperpilot.click.util.Constants;

public class Render3DSystem extends EntitySystem {
    // TODO: pyros2097's scene3d?

    private PerspectiveCamera camera;
    private CameraPath path;
    private ModelBatch batch;
    private Environment environment;
    private Model road, city, hero;
    private ModelInstance roadInstance, cityInstance, heroInstance;
    public ModelInstance enemyInstance;
    public boolean over;

    private CameraPath overview = new CameraPath(new Vector3(0, 3, 6), new Vector3(0, 3, 6), new Vector3(0, 0, 0), 6);
    private CameraPath[] paths = new CameraPath[]{
            // overview (pan left)
            new CameraPath(new Vector3(1, 3, 6), new Vector3(-3, 3, 6), new Vector3(0, 0, 0), 6),
            // overview (pan right)
            new CameraPath(new Vector3(-1, 3, 6), new Vector3(3, 3, 6), new Vector3(0, 0, 0), 6),
            // pan on hero's face
            new CameraPath(new Vector3(-7.2f, 3.2f, 1f), new Vector3(-5.6f, 3.2f, 1f), new Vector3(-6.4f, 1.8f, 0), 6),
            // pan on enemy's face
            new CameraPath(new Vector3(7.2f, 3.2f, 1f), new Vector3(5.6f, 3.2f, 1f), new Vector3(6.4f, 1.8f, 0), 6),
            // tilted view (focus on hero)
            new CameraPath(new Vector3(-6.4f, 4, 5), new Vector3(-4.8f, 4, 5), new Vector3(0, 0, 0), 6),
            // tilted view (focus on enemy)
            new CameraPath(new Vector3(6.4f, 4, 5), new Vector3(4.8f, 4, 5), new Vector3(0, 0, 0), 6),
    };

    public Render3DSystem() {
        super(9);
    }

    @Override
    public void addedToEngine(Engine engine) {
        path = paths[MathUtils.random(paths.length - 1)];

        batch = new ModelBatch();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.0f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        camera = new PerspectiveCamera(90, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        camera.near = .1f;
        camera.far = 100f;

        road = FlatObjectFactory.create(Main.getTexture("road"), 1/100f);
        roadInstance = new ModelInstance(road);
        roadInstance.transform.rotate(1, 0, 0, -90);

        city = FlatObjectFactory.create(Main.getTexture("city"), 1/100f);
        cityInstance = new ModelInstance(city);
        cityInstance.transform.translate(0, 3.6f, -3.6f);

        hero = FlatObjectFactory.create(Main.getTexture("ocm"), 1/200f);
        heroInstance = new ModelInstance(hero);
        heroInstance.transform.translate(-6.4f, 1.8f, 0);
    }

    @Override
    public void update (float deltaTime) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        if (!over && path.update(deltaTime)) {
            path.reset();
            CameraPath oldPath = path;
            while (oldPath == path) {
                path = paths[MathUtils.random(paths.length - 1)];
            }
        }

        batch.begin(camera);
        batch.render(roadInstance, environment);
        batch.render(cityInstance, environment);
        batch.flush();
        batch.render(heroInstance, environment);
        if (enemyInstance != null) {
            batch.render(enemyInstance, environment);
        }
        batch.end();
    }

    @Override
    public void removedFromEngine(Engine engine) {
        batch.dispose();
        road.dispose();
        city.dispose();
        hero.dispose();
    }

    void setOverviewPath() {
        path.reset();
        path = overview;
        update(0);
    }

    static class FlatObjectFactory {
        private static ModelBuilder builder = new ModelBuilder();

        static Model create(Texture texture, float scale) {
            float width = texture.getWidth() * scale;
            float height = texture.getHeight() * scale;
            return builder.createRect(
                    -width, -height, 0.0f,
                    width, -height, 0.0f,
                    width, height, 0.0f,
                    -width, height, 0.0f,
                    0, 1, 0,
                    new Material(new TextureAttribute(TextureAttribute.Diffuse, texture), new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)),
                    VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        }
    }

    private class CameraPath {
        Vector3 start;
        Vector3 end;
        Vector3 look;
        float duration;

        private float time;

        CameraPath(Vector3 start, Vector3 end, Vector3 look, float duration) {
            this.start = start;
            this.end = end;
            this.look = look;
            this.duration = duration;
        }

        boolean update(float delta) {
            time += delta;
            float distance = start.dst(end) * (time / duration);
            camera.position.set(start.cpy().add(end.cpy().sub(start).nor().scl(distance)));
            camera.direction.set(0, 0, -1);
            camera.up.set(0, 1, 0);
            camera.lookAt(look);
            camera.update();
            return time >= duration;
        }

        void reset() {
            time = 0;
        }
    }
}
