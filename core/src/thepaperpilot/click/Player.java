package thepaperpilot.click;

import com.badlogic.gdx.Preferences;
import thepaperpilot.click.screens.GameScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Player {
    private static Preferences save;
    private static ArrayList<String> attributes = new ArrayList<String>();

    public static boolean sound;
    public static boolean music;

    public static void saveSound() {
        save.putBoolean("sound", sound);
        save.putBoolean("music", music);

        save.flush();
    }

    public static void setPreferences(Preferences preferences) {
        save = preferences;
        sound = save.getBoolean("sound", true);
        music = save.getBoolean("music", true);
    }

    public static void save() {
        String attributes = Arrays.toString(Player.attributes.toArray(new String[Player.attributes.size()])).replaceAll(", ", ",");
        save.putString("attributes", attributes.substring(1, attributes.length() - 1));

        save.flush();
    }

    public static void load() {
        Player.attributes.clear();
        String[] attributes = save.getString("attributes", "").split(",");
        Collections.addAll(Player.attributes, attributes);

        Main.changeScreen(new GameScreen());
    }

    public static void reset() {
        save.remove("attributes");

        load();
    }

    public static boolean getAttribute(String attribute) {
        return attributes.contains(attribute);
    }

    public static void addAttribute(String attribute) {
        attributes.add(attribute);
    }
}
