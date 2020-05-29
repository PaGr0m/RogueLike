package ru.itmo.roguelike.characters.projectiles;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.characters.movement.MoverDrunkStraight;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.Tile;
import ru.itmo.roguelike.field.TileType;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;
import java.util.Optional;

/**
 * Magic ability which only Player can use
 */
public class Fireball extends Projectile {
    private static final int SPEED = 10;
    private Player actor;

    {
        damage = 10;
        mover = new MoverDrunkStraight();
    }

    public Fireball(IntCoordinate direction, Actor actor) {
        super((g, x, y) -> g.fillOval(x, y, 10, 10));
        this.drawableDescriptor.setColor(Color.YELLOW);
        this.direction = direction;
        if (actor instanceof Player) {
            this.actor = (Player) actor;
            //increase damage according to player level
            damage *= ((Player) actor).getLevel();
            System.out.println("Damage is " + damage);
        }
    }

    /**
     * Collides with another object and disappears
     *
     * @param c --- object with which this collided
     */
    @Override
    public void collide(Collidable c) {
        if (c instanceof Enemy) {
            ((Enemy) c).strike(this.damage, actor);
        }
        if (c instanceof Player) {
            return;
        }
        this.die();
    }

    @Override
    public void act(Field field) {
        Optional<Tile> t = field.getTile(position);
        if (t.isPresent() && t.get().getType() == TileType.ROCK) {
            t.get().reInit(0.5f);
            die();
        }
        IntCoordinate delta = new IntCoordinate(direction);
        delta.mult(SPEED);
        position = mover.move(position, delta);
    }

    public void setPosition(IntCoordinate position) {
        this.position = new IntCoordinate(position);
    }
}
