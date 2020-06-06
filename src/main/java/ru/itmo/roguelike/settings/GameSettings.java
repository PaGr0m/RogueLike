package ru.itmo.roguelike.settings;

/**
 * Game characteristics like FPS, Window title
 */
public final class GameSettings {
    public static final String WINDOW_TITLE = "Roguelike";
    public static final int FPS = 45;
    /**
     * Normal speed of Player in coordinates on map
     */
    public static final int STEP = 10;

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    public static String FILENAME = null;

    private GameSettings() {
    }

    public static String getSaveFileName() {
        return (FILENAME == null ? "auto.file" : FILENAME) + "_save";
    }

    public final static class ImagePath {
        public static final String TELEPORT_IN = "pic/teleport_in.png";
        public static final String TELEPORT_OUT = "pic/teleport_out.png";
        public static final String SWORD = "pic/sword.png";
        public static final String FIREBALL = "pic/fire.png";
        public static final String HEAVY_ARMOR = "pic/heavy_armor.png";
        public static final String MEDIUM_ARMOR = "pic/medium_armor.png";
        public static final String LIGHT_ARMOR = "pic/light_armor.png";
    }
}
