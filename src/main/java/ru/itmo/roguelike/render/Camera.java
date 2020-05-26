package ru.itmo.roguelike.render;

//TODO: Add comments

/**
 * Class that imitates camera
 */
public class Camera {

    // where we need to go
    private int posX = 0;
    private int posY = 0;

    // where camera now
    private float delayedX = posX;
    private float delayedY = posY;

    private float velocityX = 0;
    private float velocityY = 0;

    private final static float SPEED = 3;
    private final static float ACCEL = 0.03f;
    private final static float FRICT = 0.3f;

    /**
     * Updates velocity and delayed
     */
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

    /**
     * Move camera by X axis
     *
     * @param dx size of movement
     */
    public void moveX(int dx) {
        posX += dx;
    }

    /**
     * Mova camera by Y axis
     *
     * @param dy size of movement
     */
    public void moveY(int dy) {
        posY += dy;
    }

    /**
     * Get coordinates according to camera
     * @param x coordinates in grid world
     * @return x coordinate according to camera
     */
    public int transformX(int x) {
        return x - getPosX();
    }

    /**
     * Get coordinates according to camera
     * @param y coordinates in grid world
     * @return y coordinate according to camera
     */
    public int transformY(int y) {
        return y - getPosY();
    }
}
