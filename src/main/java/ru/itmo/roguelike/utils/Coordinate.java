package ru.itmo.roguelike.utils;

public class Coordinate {
    private float positionX;
    private float positionY;

    public Coordinate(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public float getX() {
        return positionX;
    }

    public void setX(float posX) {
        this.positionX = posX;
    }

    public float getY() {
        return positionY;
    }

    public void setY(float posY) {
        this.positionY = posY;
    }

    public void translateX(float deltaX) {
        positionX += deltaX;
    }

    public void translateY(float deltaY) {
        positionY += deltaY;
    }
}
