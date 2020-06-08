package ru.itmo.roguelike.settings;

import ru.itmo.roguelike.LaunchWindow;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.Paths;

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

    private static final String map_file_extension = "mapfile";
    private static final String save_file_add_extension = "_save";

    public static String MAP_FILE_NAME = null;
    public static String SAVE_FILE_NAME = null;
    public static LaunchWindow.Configuration MAP_FILE_CHOOSE = new LaunchWindow.Configuration(
            "Auto-generate map",
            "Load map from file",
            "SELECT MAP FILE",
            "LOAD",
            new FileNameExtensionFilter("MAP FILE", map_file_extension)
    );
    public static LaunchWindow.Configuration SAVE_FILE_CHOOSE = new LaunchWindow.Configuration(
            "New game",
            "Load game save",
            "SELECT SAVE FILE",
            "LOAD",
            new FileNameExtensionFilter("GAME SAVE FILE", map_file_extension + save_file_add_extension)
    );
    public static LaunchWindow.Configuration ON_EXIT_SAVE_FILE_CHOOSE = new LaunchWindow.Configuration(
            "Exit",
            "Save and exit",
            "SELECT SAVE FILE",
            "SAVE",
            new FileNameExtensionFilter("GAME SAVE FILE", map_file_extension + save_file_add_extension)
    );

    private GameSettings() {
    }

    public static String getSaveFileName() {
        if (SAVE_FILE_NAME != null) {
            return SAVE_FILE_NAME;
        }
        return (MAP_FILE_NAME == null ? "auto." + map_file_extension : MAP_FILE_NAME) + save_file_add_extension;
    }

    public static String simplify(String path) {
        return Paths.get(path).getFileName().toString();
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
