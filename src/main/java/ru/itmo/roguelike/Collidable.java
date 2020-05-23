package ru.itmo.roguelike;

import ru.itmo.roguelike.utils.Coordinate;

/**
 * Class that specify all items (tiles, bonus-items, mobs, player) which can interact with each other
 */
public interface Collidable {
    /**
     * @return coordinate on map of object
     */
    Coordinate getCoordinate();

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
