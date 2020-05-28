package ru.itmo.roguelike.render;

import ru.itmo.roguelike.utils.FloatCoordinate;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.util.Optional;

public class Camera {
    private static final IntCoordinate minBoundForPos = new IntCoordinate(-10, -10);
    private static final IntCoordinate maxBoundForPos = new IntCoordinate(810, 610); // FIXME MAGIC

    private final static float SPEED = 3;
    private final static float ACCEL = 0.03f;
    private final static float FRICT = 0.3f;
    private FloatCoordinate delayed = FloatCoordinate.getZeroPosition();
    private FloatCoordinate velocity = FloatCoordinate.getZeroPosition();

    public Optional<IntCoordinate> transformAndGet(IntCoordinate pos) {
        int x = transformX(pos.getX());
        int y = transformY(pos.getY());
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
        delayed.setX(x);
        delayed.setY(y);
        velocity = FloatCoordinate.getZeroPosition();
    }

    public void update(IntCoordinate pos) {
        FloatCoordinate force = new FloatCoordinate(pos);
        force.substract(delayed);

        // TODO: Seems like it's never used
        double forceLen = Math.sqrt(force.lenL2());

        if (velocity.lenL2() > SPEED * SPEED) {
            delayed.add(new FloatCoordinate(velocity));
        }

        velocity.mult(-FRICT);
        velocity.add(force, ACCEL);
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
