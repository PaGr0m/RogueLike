package ru.itmo.roguelike.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

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
}
