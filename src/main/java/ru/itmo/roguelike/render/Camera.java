package ru.itmo.roguelike.render;

import ru.itmo.roguelike.utils.Coordinate;

public class Camera {
    private Coordinate coordinate = new Coordinate(0, 0);

    private float delayedX = coordinate.getX();
    private float delayedY = coordinate.getY();

    private float velocityX = 0;
    private float velocityY = 0;

    private final static float SPEED = 3;
    private final static float ACCEL = 0.03f;
    private final static float FRICT = 0.3f;

    public void update() {
        float forceX = (coordinate.getX() - delayedX);
        float forceY = (coordinate.getY() - delayedY);

        double forceLen = Math.sqrt(forceX * forceX + forceY * forceY);

        if (velocityX * velocityX + velocityY * velocityY > SPEED * SPEED) {
            delayedX += velocityX;
            delayedY += velocityY;
        }

        velocityX += ACCEL * forceX - FRICT * velocityX;
        velocityY += ACCEL * forceY - FRICT * velocityY;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public float transformX(float x) {
        return x - getCoordinate().getX();
    }

    public float transformY(float y) {
        return y - getCoordinate().getY();
    }
}
