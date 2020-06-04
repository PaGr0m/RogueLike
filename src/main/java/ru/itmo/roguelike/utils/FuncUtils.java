package ru.itmo.roguelike.utils;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.inventory.Usable;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;

public abstract class FuncUtils {

    public static void renderImage(Graphics2D graphics, int x, int y, int width, int height, Image image) {
        float prop = (float) image.getWidth(null) / image.getHeight(null);
        int expectedWidth = (int) (prop * height);
        int expectedHeight = (int) (width / prop);

        if (expectedHeight > height) {
            x += (width - expectedWidth) / 2;
            expectedHeight = height;
        } else {
            y += (height - expectedHeight) / 2;
            expectedWidth = width;
        }
        graphics.drawImage(image, x, y, expectedWidth, expectedHeight, null);
    }

    public interface UsableCreator {
        Usable create(DataInputStream inputStream, Player p) throws IOException;
    }

}
