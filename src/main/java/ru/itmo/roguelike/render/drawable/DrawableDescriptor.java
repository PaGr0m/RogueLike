package ru.itmo.roguelike.render.drawable;

import java.awt.*;

public class DrawableDescriptor {
    private float x;
    private float y;

    public int width;
    public int height;

    private Color color;
    public char pictogram;

    public DrawableDescriptor setX(float x) {
        this.x = x;
        return this;
    }

    public float getX() {
        return x;
    }

    public DrawableDescriptor setY(float y) {
        this.y = y;
        return this;
    }

    public float getY() {
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
