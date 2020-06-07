package ru.itmo.roguelike.utils;

public class BoundingBox {
    private final IntCoordinate leftTop;
    private final IntCoordinate rightBottom;
    private final IntCoordinate center;

    private final int width;
    private final int height;

    public BoundingBox(int width, int height, IntCoordinate center) {
        this.width = width;
        this.height = height;

        this.leftTop = new IntCoordinate(center);
        this.leftTop.substract(new IntCoordinate(width / 2, height / 2));

        this.rightBottom = new IntCoordinate(center);
        this.rightBottom.add(new IntCoordinate(width / 2, height / 2));

        this.center = new IntCoordinate(center);
    }

    public IntCoordinate getCenter() {
        return center;
    }

    public IntCoordinate getLeftTop() {
        return leftTop;
    }

    public IntCoordinate getRightBottom() {
        return rightBottom;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
