package ru.itmo.roguelike.utils;

import java.util.Objects;

/**
 * Mutable R^2 coordinates
 */
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

    /**
     * @return new (0, 0) position
     */
    public static IntCoordinate getZeroPosition() {
        return new IntCoordinate(0, 0);
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

    /**
     * @return (signum ( x), signum(y))
     */
    public IntCoordinate signum() {
        return new IntCoordinate(Integer.signum(posX), Integer.signum(posY));
    }

    /**
     * @return (- x, - y)
     */
    public IntCoordinate inverse() {
        return new IntCoordinate(-posX, -posY);
    }

    /**
     * @return L_1 distance
     */
    public int lenL1() {
        return Math.abs(posX) + Math.abs(posY);
    }

    @Override
    public String toString() {
        return String.format("ICoord[%d, %d]", posX, posY);
    }

    /**
     * @return L_2 distance
     */
    public int lenL2() {
        return this.posX * this.posX + this.posY * this.posY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntCoordinate that = (IntCoordinate) o;
        return posX == that.posX &&
                posY == that.posY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }
}
