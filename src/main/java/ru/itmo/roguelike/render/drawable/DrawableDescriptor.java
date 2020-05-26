package ru.itmo.roguelike.render.drawable;

import java.awt.*;

//TODO: Add comments

/**
 * Describes F pf
 */
public class DrawableDescriptor {
    private int x;
    private int y;

    public int width;
    public int height;

    private Color color;
    public char pictogram;

    public DrawableDescriptor setX(int x) {
        this.x = x;
        return this;
    }

    public int getX() {
        return x;
    }

    public DrawableDescriptor setY(int y) {
        this.y = y;
        return this;
    }

    public int getY() {
        return y;
    }

    public DrawableDescriptor setColor(Color color) {
        this.color = color;
        return this;
    }

    public Color getColor() {
        return color;
    }
}
