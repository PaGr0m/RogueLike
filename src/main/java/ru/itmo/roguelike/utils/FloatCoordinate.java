package ru.itmo.roguelike.utils;

public class FloatCoordinate {
    private float posX;
    private float posY;

    public FloatCoordinate(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public FloatCoordinate(FloatCoordinate pos) {
        this.posX = pos.posX;
        this.posY = pos.posY;
    }

    public float getX() {
        return posX;
    }

    public void setX(float posX) {
        this.posX = posX;
    }

    /**
     * @return new (0, 0) position
     */
    public static FloatCoordinate getZeroPosition() {
        return new FloatCoordinate(0, 0);
    }

    public float getY() {
        return posY;
    }

    public void setY(float posY) {
        this.posY = posY;
    }

    public void add(FloatCoordinate other) {
        posX += other.posX;
        posY += other.posY;
    }

    public void mult(float d) {
        posY *= d;
        posX *= d;
    }

    public void div(float d) {
        posY /= d;
        posX /= d;
    }

    public float lenL1() {
        return Math.abs(posX) + Math.abs(posY);
    }

    @Override
    public String toString() {
        return String.format("FCoord[%f, %f]", posX, posY);
    }
}
