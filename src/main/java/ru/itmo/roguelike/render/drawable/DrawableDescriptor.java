package ru.itmo.roguelike.render.drawable;

import java.awt.*;

public class DrawableDescriptor {
    public int width;
    public int height;
    public char pictogram;
    private int x;
    private int y;
    private Color color;

    public int getX() {
        return x;
    }

    public DrawableDescriptor setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public DrawableDescriptor setY(int y) {
        this.y = y;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public DrawableDescriptor setColor(Color color) {
        this.color = color;
        return this;
    }
}
