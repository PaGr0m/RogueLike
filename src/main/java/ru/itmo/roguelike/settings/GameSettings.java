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

    public static String getSaveFileName() {
        return (FILENAME == null ? "auto.file" : FILENAME) + "_save";
    }

    private GameSettings() {
    }
}
