package ru.itmo.roguelike.render;

import ru.itmo.roguelike.utils.FloatCoordinate;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.util.Optional;

public class Camera {
    private static final IntCoordinate minBoundForPos = new IntCoordinate(-10, -10);
    private static final IntCoordinate maxBoundForPos = new IntCoordinate(610, 610); // FIXME MAGIC

    private final static float SPEED = 3;
    private final static float ACCEL = 0.03f;
    private final static float FRICT = 0.3f;
    private FloatCoordinate delayed = new FloatCoordinate(0, 0);
    private float velocityX = 0;
    private float velocityY = 0;

    public Optional<IntCoordinate> transformAndGet(int x, int y) {
        x = transformX(x);
        y = transformY(y);
        if (x < minBoundForPos.getX()
                || x > maxBoundForPos.getX()
                || y < minBoundForPos.getY()
                || y > maxBoundForPos.getY()
        ) {
            return Optional.empty();
        }
        return Optional.of(new IntCoordinate(x, y));
    }

    public void moveForce(float x, float y) {
        delayedX = x;
        delayedY = y;
        velocityX = 0;
        velocityY = 0;
    }

    public void update(int posX, int posY) {
        float forceX = (posX - delayed.getX());
        float forceY = (posY - delayed.getY());

        // TODO: Seems like it's never used
        double forceLen = Math.sqrt(forceX * forceX + forceY * forceY);

        if (velocityX * velocityX + velocityY * velocityY > SPEED * SPEED) {
            delayed.addToX(velocityX);
            delayed.addToY(velocityY);
        }

        velocityX += ACCEL * forceX - FRICT * velocityX;
        velocityY += ACCEL * forceY - FRICT * velocityY;
    }

    public int getPosX() {
        return (int) delayed.getX();
    }

    public int getPosY() {
        return (int) delayed.getY();
    }

    public int transformX(int x) {
        return (int) (x - delayed.getX());
    }

    public int transformY(int y) {
        return (int) (y - delayed.getY());
    }
}
