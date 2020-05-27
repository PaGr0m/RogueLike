package ru.itmo.roguelike;

import ru.itmo.roguelike.utils.IntCoordinate;

/**
 * Class that specify all items (tiles, bonus-items, mobs, player) which can interact with each other
 */
public interface Collidable {
    /**
     * @return coordinate on map of object
     */
    IntCoordinate getPosition();

    /**
     * @return previous coordinate on map of object. If not implemented, returns the same as <code>getPosition()</code>
     */
    default IntCoordinate getLastPosition() {
        return getPosition();
    }

    /**
     * @return width of object in coordinates on map
     */
    int getWidth();

    /**
     * @return height of object in coordinates on map
     */
    int getHeight();

    /**
     * Method that specify what exactly do object when collide other
     *
     * @param c --- object with which this collided
     */
    void collide(Collidable c);
}
