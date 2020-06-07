package ru.itmo.roguelike.render;

import ru.itmo.roguelike.settings.GameSettings;
import ru.itmo.roguelike.utils.FloatCoordinate;
import ru.itmo.roguelike.utils.IntCoordinate;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class Camera {
    private static final int boundsPadding = 10;
    private static final IntCoordinate minBoundForPos = new IntCoordinate(-boundsPadding, -boundsPadding);
    private static final IntCoordinate maxBoundForPos = new IntCoordinate(
            GameSettings.WINDOW_WIDTH + boundsPadding,
            GameSettings.WINDOW_HEIGHT - boundsPadding
    );

    private static final IntCoordinate centerShift = new IntCoordinate(-GameSettings.WINDOW_WIDTH / 2, -GameSettings.WINDOW_HEIGHT / 2);

    private final static float SPEED = 3;
    private final static float ACCEL = 0.03f;
    private final static float FRICT = 0.6f;

    /**
     * Position of the camera. Coordinates are called "delayed", because camera is smoothly following player, and there
     * is some delay, before their coordinates will be equal.
     */
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

    public void moveForce(IntCoordinate position) {
        position = new IntCoordinate(position);
        position.add(centerShift);

        delayed.setX(position.getX());
        delayed.setY(position.getY() + 100);

        velocity = FloatCoordinate.getZeroPosition();
    }

    /**
     * Updates velocity and delayed
     */
    public void update(IntCoordinate pos) {
        pos = new IntCoordinate(pos);
        pos.add(centerShift);

        FloatCoordinate force = new FloatCoordinate(pos);
        force.substract(delayed);

        if (velocity.lenL2() > SPEED * SPEED) {
            delayed.add(new FloatCoordinate(velocity));
        }

        velocity.add(velocity, -FRICT);
        velocity.add(force, ACCEL);
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
