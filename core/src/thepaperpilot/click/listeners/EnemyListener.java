package thepaperpilot.click.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import thepaperpilot.click.components.EnemyComponent;
import thepaperpilot.click.systems.EnemySystem;
import thepaperpilot.click.systems.Render3DSystem;
import thepaperpilot.click.util.Mappers;

public class EnemyListener implements EntityListener {

    private EnemySystem system;

    public EnemyListener(EnemySystem system) {
        this.system = system;
    }

    @Override
    public void entityAdded(Entity entity) {
        EnemyComponent ec = Mappers.enemy.get(entity);

        ModelInstance enemyInstance = new ModelInstance(ec.model);
        enemyInstance.transform.translate(6.4f, 1.8f, 0);
        system.getEngine().getSystem(Render3DSystem.class).enemyInstance = enemyInstance;
    }

    @Override
    public void entityRemoved(Entity entity) {
        Mappers.enemy.get(entity).model.dispose();

        int index = system.enemies.indexOf(entity) + 1;
        if (index == -1) return;
        if (system.enemies.size() == index) return;
        Entity newEntity = system.enemies.get(system.enemies.indexOf(entity) + 1);
        newEntity.add(Mappers.dialogue.get(entity));
        Mappers.dialogue.get(newEntity).start = Mappers.enemy.get(newEntity).start;
        system.getEngine().addEntity(newEntity);
    }
}
