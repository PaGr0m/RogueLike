package ru.itmo.roguelike.utils;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public class FileUtils {

    public static Image loadImage(String path) {
        try {
            return ImageIO.read(getStream(path));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        throw new RuntimeException("Cannot find resource file \"" + path + "\"");
    }

    public static @NotNull InputStream getStream(String path) {
        return Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(path));
    }

}
