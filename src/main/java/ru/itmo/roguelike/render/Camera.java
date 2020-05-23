package ru.itmo.roguelike.render;

public class Camera {
    private final static float SPEED = 3;
    private final static float ACCEL = 0.03f;
    private final static float FRICT = 0.3f;
    private float delayedX = 0;
    private float delayedY = 0;
    private float velocityX = 0;
    private float velocityY = 0;

    public void update(int posX, int posY) {
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

    public int getPosY() {
        return (int) delayedY;
    }

    public int transformX(int x) {
        return x - getPosX();
    }

    public int transformY(int y) {
        return y - getPosY();
    }
}
