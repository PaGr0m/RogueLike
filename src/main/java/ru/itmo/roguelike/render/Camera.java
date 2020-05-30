package ru.itmo.roguelike.render;

import ru.itmo.roguelike.utils.FloatCoordinate;
import ru.itmo.roguelike.utils.IntCoordinate;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class Camera {
    private static final IntCoordinate minBoundForPos = new IntCoordinate(-10, -10);
    private static final IntCoordinate maxBoundForPos = new IntCoordinate(810, 610); // FIXME MAGIC

    private final static float SPEED = 3;
    private final static float ACCEL = 0.03f;
    private final static float FRICT = 0.6f;
    private final FloatCoordinate delayed = FloatCoordinate.getZeroPosition();
    private FloatCoordinate velocity = FloatCoordinate.getZeroPosition();

    /**
     * Return position according to camera
     *
     * @return Position in local camera coordinates. Value <code>Optional.empty()</code> is returned  when the object is
     * outside of camera scope.
     */
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

    /**
     * Updates velocity and delayed
     */
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

    /**
     * @return Camera position in world coordinates
     */
    public IntCoordinate getCenter() {
        IntCoordinate res = delayed.toIntCoordinate();
        int cx = (minBoundForPos.getX() + maxBoundForPos.getX()) / 2;
        int cy = (minBoundForPos.getY() + maxBoundForPos.getY()) / 2;
        res.add(new IntCoordinate(cx, cy));
        return res;
    }

    public void transform(IntCoordinate coordinate) {
        coordinate.substract(delayed.toIntCoordinate());
    }
}
