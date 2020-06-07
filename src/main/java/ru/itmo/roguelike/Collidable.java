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
     *
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
     * @return the transformation to be applied to the shape (see {@link Collidable#getShape()}) after translation
     * Needs since different transformations do not commute
     */
    default AffineTransform getAdditionalTransform() {
        return new AffineTransform();
    }

    /**
     * <ol>
     *     <li> translates shape (see {@link Collidable#getShape()}) to current coordinates </li>
     *     <li> applies additional transformation (see {@link Collidable#getAdditionalTransform()}) </li>
     * </ol>
     *
     * @return shape with correct position in world coordinates
     */
    default Shape getShapeAtPosition() {
        return getShapeAtPosition(getPosition());
    }

    default Shape getShapeAtPosition(IntCoordinate position) {
        AffineTransform transform = new AffineTransform();
        transform.translate(position.getX(), position.getY());
        transform.concatenate(getAdditionalTransform());
        return transform.createTransformedShape(getShape());
    }
}
