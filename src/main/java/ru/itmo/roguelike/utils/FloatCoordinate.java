package ru.itmo.roguelike.utils;

public class FloatCoordinate {
    private float posX;
    private float posY;

    public FloatCoordinate(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public float getX() {
        return posX;
    }

    public void setX(float posX) {
        this.posX = posX;
    }

    public void addToX(float dx) {
        this.posX += dx;
    }

    public float getY() {
        return posY;
    }

    public void setY(float posY) {
        this.posY = posY;
    }

    public void addToY(float dy) {
        this.posY += dy;
    }
}
