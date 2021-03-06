package thepaperpilot.click.util;

public class Constants {
    /* Sizes */
    public static final int WORLD_WIDTH = 1280;
    public static final int WORLD_HEIGHT = 720;
    public static final int UI_WIDTH = 300;
    public static final int DIALOGUE_SIZE = 240;
    public static final float RUNE_ZOOM = 3;
    public static final float FACE_SIZE = DIALOGUE_SIZE / 4f;
    public static final float MAP_MARGIN = 40;

    /* Speeds */
    public static final float TILE_SPEED = 600;
    public static final float RUNE_EXIT_TIME = 1.5f;
    public static final float IDLE_CHANCE = .0002f;
    public static final float STABLE_TIME = 1;
    public static final float TEXT_SPEED = 32;
    public static final float PARTICLE_FREQUENCY = 1/100f;
    public static final float RUNE_DELAY = .1f;
    public static final float TRANSITION_TIME = .5f;

    /* Audio */
    public static final float MASTER_VOLUME = 1;

    /* Balancing */
    public static final int DESTROY_LOW = 7;
    public static final int DESTROY_HIGH = 9;
    public static final int POINTS_PER_LEVEL = 4;
    public static final int BASE_EXP = 27;
    public static final float EXP_CURVE = 2.1f;
    public static final int MAX_SPELLS = 6;
    public static final int PROFICIENCY_BONUS_FREQUENCY = 8;
    public static final int BASE_HEALTH = 20;
    public static final float HEALTH_CURVE = 1.1f;
    public static final float BASE_SKILL_EFFECT = .01f; // .01f == 1% chance
    public static final float BASE_RUNES = .5f;
    public static final int BASE_RUNES_MAX = 40;
    public static final float BASE_RUNES_MAX_CURVE = .05f;

    /* Debug */
    public static final boolean DEBUG = true;
    public static final boolean PROFILING = false;
    public static boolean PLAYERLESS = false;
    public static boolean UNDYING = false;
    public static float DELTA_MOD = 1;

    /* Other */
    public static final int MATCH_4_RUMBLE = 100;
    public static final int MATCH_5_RUMBLE = 200;
}
