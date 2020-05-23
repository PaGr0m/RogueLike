package ru.itmo.roguelike.characters.projectiles;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.mobs.Enemy;

import java.awt.*;

/**
 * Magic ability which only Player can use
 */
public class Fireball extends Projectile {
    private final int speed = 10;

    {
        damage = 10;
    }

    /**
     * Collides with another object and disappears
     *
     * @param c --- object with which this collided
     */
    @Override
    public void collide(Collidable c) {
        if (c instanceof Enemy) {
            ((Enemy) c).strike(this.damage);
        }
        this.die();
    }

    @Override
    public void go() {
        coordinate.setX(coordinate.getX() + direction.getX() * speed);
        coordinate.setY(coordinate.getY() + direction.getY() * speed);
    }

    @Override
    public void draw() {
        drawableDescriptor.setX(coordinate.getX())
                          .setY(coordinate.getY())
                          .setColor(new Color(0x9917FF));
    }
}
