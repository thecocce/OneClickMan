package thepaperpilot.click.util;

import com.badlogic.ashley.core.ComponentMapper;
import thepaperpilot.click.components.ActorComponent;
import thepaperpilot.click.components.DialogueComponent;
import thepaperpilot.click.components.EnemyComponent;

public class Mappers {
    public static final ComponentMapper<DialogueComponent> dialogue = ComponentMapper.getFor(DialogueComponent.class);
    public static final ComponentMapper<ActorComponent> actor = ComponentMapper.getFor(ActorComponent.class);
    public static final ComponentMapper<EnemyComponent> enemy = ComponentMapper.getFor(EnemyComponent.class);
}
