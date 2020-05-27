package ru.itmo.roguelike.utils;

public class IntCoordinate {
    private int posX;
    private int posY;

    public IntCoordinate(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public IntCoordinate(IntCoordinate pos) {
        this.posX = pos.posX;
        this.posY = pos.posY;
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

    public void set(IntCoordinate pos) {
        this.posX = pos.posX;
        this.posY = pos.posY;
    }

    public void add(IntCoordinate other) {
        posX += other.posX;
        posY += other.posY;
    }

    public void substract(IntCoordinate other) {
        posX -= other.posX;
        posY -= other.posY;
    }

    public void mult(int d) {
        posY *= d;
        posX *= d;
    }

    public void mult(int dx, int dy) {
        posX *= dx;
        posY *= dy;
    }

    public void div(int d) {
        posY /= d;
        posX /= d;
    }

    public IntCoordinate signum() {
        return new IntCoordinate(Integer.signum(posX), Integer.signum(posY));
    }

    public int lenL1() {
        return Math.abs(posX) + Math.abs(posY);
    }

    public int lenL2() {
        return this.posX * this.posX + this.posY * this.posY;
    }
}
