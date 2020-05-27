package ru.itmo.roguelike.render.drawable;

import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;

public class DrawableDescriptor {
    public int width;
    public int height;
    public char pictogram;
    private final IntCoordinate position = IntCoordinate.getZeroPosition();
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
}
