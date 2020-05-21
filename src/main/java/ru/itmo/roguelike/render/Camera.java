package ru.itmo.roguelike.render;

public class Camera {
    private int posX = 0;
    private int posY = 0;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void moveX(int dx) {
        posX += dx;
    }

    public void moveY(int dy) {
        posY += dy;
    }

    public int transformX(int x) {
        return x + posX;
    }

    public int transformY(int y) {
        return y + posY;
    }
}
