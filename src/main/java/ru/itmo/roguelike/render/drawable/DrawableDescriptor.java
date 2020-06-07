package ru.itmo.roguelike.render.drawable;

import ru.itmo.roguelike.utils.BoundingBox;
import ru.itmo.roguelike.utils.IntCoordinate;
import ru.itmo.roguelike.utils.Pair;

import java.awt.*;

/**
 * Descriptor of every drawable object. It describes, how the object should be drawn. The description consist of:
 * <ul>
 *     <li>width and height</li>
 *     <li>color</li>
 *     <li>shape</li>
 *     <li>position</li>
 *     <li>and color</li>
 * </ul>
 */
public class DrawableDescriptor {
    private final IntCoordinate position = IntCoordinate.getZeroPosition();
    public int width = 10;
    public int height = 10;
    public char pictogram;
    private Color color;

    public IntCoordinate getPosition() {
        return position;
    }

    public void setPosition(IntCoordinate position) {
        this.position.set(position);
    }

    public Color getColor() {
        return color;
    }

    public DrawableDescriptor setColor(Color color) {
        this.color = color;
        return this;
    }

    public BoundingBox boundingBox() {
        return new BoundingBox(width, height, position);
    }
}
