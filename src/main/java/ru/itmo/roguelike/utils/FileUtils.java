package ru.itmo.roguelike.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class FileUtils {

    public static Image loadImage(String path) {
        try {
            final URL url = ClassLoader.getSystemClassLoader().getResource(path);
            if (url != null) {
                return ImageIO.read(url);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        throw new RuntimeException("Cannot find resource file \"" + path + "\"");
    }

    public static File getFile(String path) {
        final URL url = ClassLoader.getSystemClassLoader().getResource(path);
        if (url != null) {
            try {
                return Paths.get(url.toURI()).toFile();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
