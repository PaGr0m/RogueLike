package ru.itmo.roguelike.render;

public class Camera {
    private int posX = 0;
    private int posY = 0;

    private float delayedX = posX;
    private float delayedY = posY;

    private float velocityX = 0;
    private float velocityY = 0;

    private final static float SPEED = 3;
    private final static float ACCEL = 0.03f;
    private final static float FRICT = 0.3f;

    public void update() {
        float forceX = (posX - delayedX);
        float forceY = (posY - delayedY);

        double forceLen = Math.sqrt(forceX * forceX + forceY * forceY);

        if (velocityX * velocityX + velocityY * velocityY > SPEED * SPEED) {
            delayedX += velocityX;
            delayedY += velocityY;
        }

        velocityX += ACCEL * forceX - FRICT * velocityX;
        velocityY += ACCEL * forceY - FRICT * velocityY;
    }

    public int getPosX() {
        return (int) delayedX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return (int) delayedY;
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
        return x + getPosX();
    }

    public int transformY(int y) {
        return y + getPosY();
    }
}
