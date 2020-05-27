package ru.itmo.roguelike.characters.projectiles;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;

/**
 * Magic ability which only Player can use
 */
public class Fireball extends Projectile {
    private final int speed = 10;

    {
        damage = 10;
    }

    public Fireball(IntCoordinate direction) {
        super((g, x, y) -> g.fillOval(x, y, 10, 10));
        this.drawableDescriptor.setColor(Color.YELLOW);
        this.direction = direction;
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
    public void act(Field field) {
        super.act(field);
        IntCoordinate other = new IntCoordinate(this.direction);
        other.mult(this.speed);
        this.getPosition().add(other);
    }

}
