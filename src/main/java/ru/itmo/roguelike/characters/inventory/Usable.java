package ru.itmo.roguelike.characters.inventory;

import ru.itmo.roguelike.characters.Actor;

import java.awt.*;

/**
 * All items that can be used by some actor
 */
public interface Usable {
    static void renderImageInInventory(Graphics2D graphics, int x, int y, int width, int height, Image image) {
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

    /**
     * Activates effect of usage when used by specified actor.
     */
    void use(Actor actor);

    /**
     * Some usable items may be used only once, another – limited amount of time, and the other – infinitely.
     *
     * @return {@code true} if this item is still may be used.
     */
    boolean isUsed();

    /**
     * Render object picture in inventory
     *
     * @param graphics graphics2D object from UIManager
     * @param x        x coordinate of left upper corner of inventory cell
     * @param y        y coordinate of left upper corner of inventory cell
     * @param width    width of inventory cell
     * @param height   height of inventory cell
     */
    void renderInInventory(Graphics2D graphics, int x, int y, int width, int height);
}
