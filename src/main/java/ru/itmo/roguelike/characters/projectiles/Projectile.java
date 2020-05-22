package ru.itmo.roguelike.characters.projectiles;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.drawable.Drawable;

/**
 * Class of projectile objects
 */
public class Projectile extends Actor {
    @Override
    public void go() {

    }

    @Override
    public void die() {
        Drawable.unregister(this);
        Projectile.unregister(this);
    }

    /**
     * Method that specify what exactly do object when collide other
     *
     * @param c --- object with which this collided
     */
    @Override
    public void collide(Collidable c) {

    }

    @Override
    public void draw() {

    }
}
