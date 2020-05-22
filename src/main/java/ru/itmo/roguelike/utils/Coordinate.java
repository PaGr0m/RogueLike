package ru.itmo.roguelike.utils;

public class Coordinate {
    private float positionX;
    private float positionY;

    public Coordinate(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public float getX() {
        return positionX;
    }

    public void setX(int posX) {
        this.positionX = posX;
    }

    public float getY() {
        return positionY;
    }

    public void setY(int posY) {
        this.positionY = posY;
    }

    public void translateX(float deltaX) {
        positionX += deltaX;
    }

    public void translateY(float deltaY) {
        positionY += deltaY;
    }
}
