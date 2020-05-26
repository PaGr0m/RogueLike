package ru.itmo.roguelike.utils;

/**
 * Class for integer coordinates
 */
public class Coordinate {
    private int posX;
    private int posY;

    /**
     * Constructor for coordinate
     *
     * @param posX --- coordinate x
     * @param posY --- cooredinate y
     */
    public Coordinate(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
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
}
