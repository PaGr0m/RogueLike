package ru.itmo.roguelike.utils;

public class IntCoordinate {
    private int posX;
    private int posY;

    public IntCoordinate(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public static Coordinate zero() {
        return new Coordinate(0, 0);
    }

    public int getX() {
        return posX;
    }

    public void setX(int posX) {
        this.posX = posX;
    }

    public int getY() {
        return posY;
    }

    public void setY(int posY) {
        this.posY = posY;
    }

    public void add(Coordinate other) {
        posX += other.posX;
        posY += other.posY;
    }

    public void div(int d) {
        posY /= d;
        posX /= d;
    }

    public int lenL1() {
        return Math.abs(posX) + Math.abs(posY);
    }
}
