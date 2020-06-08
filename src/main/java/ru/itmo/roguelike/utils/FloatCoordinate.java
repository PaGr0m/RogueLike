package ru.itmo.roguelike.utils;

/**
 * Mutable R^2 coordinates
 */
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

    public FloatCoordinate(IntCoordinate pos) {
        this.posX = pos.getX();
        this.posY = pos.getY();
    }

    /**
     * @return new (0, 0) position
     */
    public static FloatCoordinate getZeroPosition() {
        return new FloatCoordinate(0, 0);
    }

    public float getX() {
        return posX;
    }

    public void setX(float posX) {
        this.posX = posX;
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

    public void add(FloatCoordinate other, float c) {
        posX += c * other.posX;
        posY += c * other.posY;
    }

    public void substract(FloatCoordinate other) {
        posX -= other.posX;
        posY -= other.posY;
    }

    public void mult(float d) {
        posY *= d;
        posX *= d;
    }

    public void div(float d) {
        posY /= d;
        posX /= d;
    }

    /**
     * @return L_1 distance
     */
    public float lenL1() {
        return Math.abs(posX) + Math.abs(posY);
    }

    /**
     * @return angle in degrees (in range [-pi, pi]) between {@code new FloatCoordinate(1, 0)}
     * */
    public float toAngle() {
        return (float) Math.atan2(posY, posX);
    }

    public static FloatCoordinate fromAngle(float angle) {
        return new FloatCoordinate((float) Math.cos(angle), (float) Math.sin(angle));
    }

    @Override
    public String toString() {
        return String.format("FCoord[%f, %f]", posX, posY);
    }

    /**
     * @return L_2 distance
     */
    public double lenL2() {
        return this.posX * this.posX + this.posY * this.posY;
    }

    public IntCoordinate toIntCoordinate() {
        return new IntCoordinate((int) posX, (int) posY);
    }

    public IntCoordinate getSignum(float delta) {
        int x = posX > delta ? 1 : posX < -delta ? -1 : 0;
        int y = posY > delta ? 1 : posY < -delta ? -1 : 0;
        return new IntCoordinate(x, y);
    }
}
