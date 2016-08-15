package thepaperpilot.click.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import thepaperpilot.click.ui.Line;
import thepaperpilot.click.ui.Option;
import thepaperpilot.click.ui.ScrollText;

import java.util.HashMap;
import java.util.Map;

public class DialogueComponent implements Component {
    public HashMap<String, Line> lines = new HashMap<String, Line>();
    public String start = "";
    public String[] faces = new String[]{};
    public boolean leftFocused = false;
    transient public Map<String, Runnable> events = new HashMap<String, Runnable>();

    transient public String line;
    transient public Option selected;
    transient public float timer;

    transient public Image leftFace;
    transient public Image rightFace;
    transient public Table message;
    transient public ScrollText messageLabel;

    public static DialogueComponent read(String file) {
        return new Json().fromJson(DialogueComponent.class, Gdx.files.internal(file).readString());
    }
}
