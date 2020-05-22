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
        this.positionX = this.positionX + this.direction.getFirst() * this.speed;
        this.positionY = this.positionY + this.direction.getSecond() * this.speed;
    }

    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0x9917FF));
    }
}
