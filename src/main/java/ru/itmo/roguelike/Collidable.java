package ru.itmo.roguelike;

import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;
import java.awt.geom.AffineTransform;


/**
 * Class that specify all items (bonus-items, mobs, player) which can interact with each other
 */
public interface Collidable {
    Shape SQUARE_SHAPE = new Rectangle(0, 0, 10, 10);

    /**
     * @return coordinate on map of object
     */
    IntCoordinate getPosition();

    /**
     * Set position of current object.
     * @param coordinate the new coordniate for this object
     */
    void setPosition(IntCoordinate coordinate);

    /**
     * @return previous coordinate on map of object. If not implemented, returns the same as {@code getPosition()}
     */
    default IntCoordinate getLastPosition() {
        return getPosition();
    }

    /**
     * Method that specify what exactly do object when collide other
     *
     * @param c --- object with which this collided
     */
    void collide(Collidable c);

    /**
     * @return shape of the object to collide with
     * (shape position doesn't matter)
     */
    default Shape getShape() {
        return SQUARE_SHAPE;
    }

    /**
     * @return the transformation to be applied to the shape {@see getShape()} after translation
     * Needs since different transformations do not commute
     */
    default AffineTransform getAdditionalTransform() {
        return new AffineTransform();
    }

    /**
     * 1. translates shape {@see getShape()} to current coordinates
     * 2. applies additional transformation {@see getAdditionalTransform()}
     *
     * @return shape with correct position in world coordinates
     */
    default Shape getShapeAtPosition() {
        AffineTransform transform = new AffineTransform();
        transform.translate(getPosition().getX(), getPosition().getY());
        transform.concatenate(getAdditionalTransform());
        return transform.createTransformedShape(getShape());
    }
}
