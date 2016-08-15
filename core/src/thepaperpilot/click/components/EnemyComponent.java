package thepaperpilot.click.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.Model;

public class EnemyComponent implements Component {
    public String start;
    public Model model;
    public boolean invincible;

    public EnemyComponent(String start, Model model) {
        this.start = start;
        this.model = model;
    }
}
