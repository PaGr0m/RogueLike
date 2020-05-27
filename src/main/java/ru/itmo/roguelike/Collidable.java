package ru.itmo.roguelike;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Optional;

/**
 * Class that specify all items (tiles, bonus-items, mobs, player) which can interact with each other
 */
public interface Collidable {
    Shape DEFAULT_SHAPE = new Rectangle(0,0,10,10);

    /**
     * @return x coordinate on map of object
     */
    int getX();

    /**
     * @return x coordinate on map of object
     */
    int getY();

    /**
     * Method that specify what exactly do object when collide other
     *
     * @param c --- object with which this collided
     */
    void collide(Collidable c);

    default Shape getShape() {
        return DEFAULT_SHAPE;
    }

    default AffineTransform getAdditionalTransform() {
        return new AffineTransform();
    }

    default Shape getShapeAtPosition() {
        AffineTransform transform = new AffineTransform();
        transform.translate(getX(), getY());
        transform.concatenate(getAdditionalTransform());
        return transform.createTransformedShape(getShape());
    }
}
