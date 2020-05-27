package ru.itmo.roguelike.utils;

public class IntCoordinate {
    private int posX;
    private int posY;

    public IntCoordinate(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getX() {
        return posX;
    }

    public void setX(int posX) {
        this.posX = posX;
    }

    /**
     * @return new (0, 0) position
     */
    public static IntCoordinate getZeroPosition() {
        return new IntCoordinate(0, 0);
    }

    public int getY() {
        return posY;
    }

    public void setY(int posY) {
        this.posY = posY;
    }

    public void addToX(float dx) {
        this.posX += dx;
    }

    public void addToY(float dy) {
        this.posY += dy;
    }
}
