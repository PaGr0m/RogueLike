package ru.itmo.roguelike.characters.projectiles;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.characters.movement.MoverDrunkStraight;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.Coordinate;
import ru.itmo.roguelike.utils.Pair;

import java.awt.*;

/**
 * Magic ability which only Player can use
 */
public class Fireball extends Projectile {
    private static final int SPEED = 10;

    {
        damage = 10;
        mover = new MoverDrunkStraight();
    }

    public Fireball(Coordinate direction) {
        super((g, x, y) -> g.fillOval(x, y, 10, 10));
        this.drawableDescriptor.setColor(Color.YELLOW);
        this.direction = new Pair<>(direction.getX(), direction.getY());
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
        if (c instanceof Player) {
            return;
        }
        this.die();
    }

    @Override
    public void act(Field field) {
        super.act(field);
        int dx = direction.getFirst() * SPEED;
        int dy = direction.getSecond() * SPEED;
        positionX = mover.moveX(positionX, dx);
        positionY = mover.moveY(positionY, dy);
    }

}
