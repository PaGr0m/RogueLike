package ru.itmo.roguelike.render;

import ru.itmo.roguelike.utils.FloatCoordinate;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.util.Optional;

public class Camera {
    private static final IntCoordinate minBoundForPos = new IntCoordinate(-10, -10);
    private static final IntCoordinate maxBoundForPos = new IntCoordinate(810, 610); // FIXME MAGIC

    private final static float SPEED = 3;
    private final static float ACCEL = 0.03f;
    private final static float FRICT = 0.6f;
    private final FloatCoordinate delayed = FloatCoordinate.getZeroPosition();
    private FloatCoordinate velocity = FloatCoordinate.getZeroPosition();

    public Optional<IntCoordinate> transformAndGet(IntCoordinate pos) {
        pos = new IntCoordinate(pos);
        transform(pos);
        if (pos.getX() < minBoundForPos.getX()
                || pos.getX() > maxBoundForPos.getX()
                || pos.getY() < minBoundForPos.getY()
                || pos.getY() > maxBoundForPos.getY()
        ) {
            return Optional.empty();
        }
        return Optional.of(pos);
    }

    public void moveForce(float x, float y) {
        delayed.setX(x);
        delayed.setY(y);
        velocity = FloatCoordinate.getZeroPosition();
    }

    public void update(IntCoordinate pos) {
        FloatCoordinate force = new FloatCoordinate(pos);
        force.substract(delayed);

        if (velocity.lenL2() > SPEED * SPEED) {
            delayed.add(new FloatCoordinate(velocity));
        }

        velocity.add(velocity, -FRICT);
        velocity.add(force, ACCEL);
    }

    public int getPosX() {
        return (int) delayed.getX();
    }

    public int getPosY() {
        return (int) delayed.getY();
    }

    public void transform(IntCoordinate coordinate) {
        coordinate.substract(delayed.toIntCoordinate());
    }
}
