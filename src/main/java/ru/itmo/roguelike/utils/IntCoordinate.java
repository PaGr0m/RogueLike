package ru.itmo.roguelike.utils;

public class IntCoordinate {
    private int posX;
    private int posY;

    public IntCoordinate(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public IntCoordinate(IntCoordinate pos) {
        this.posX = pos.getX();
        this.posY = pos.getY();
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

    public void add(IntCoordinate other) {
        posX += other.posX;
        posY += other.posY;
    }

    public void mult(int d) {
        posY *= d;
        posX *= d;
    }

    public void div(int d) {
        posY /= d;
        posX /= d;
    }

    public int lenL1() {
        return Math.abs(posX) + Math.abs(posY);
    }
}
