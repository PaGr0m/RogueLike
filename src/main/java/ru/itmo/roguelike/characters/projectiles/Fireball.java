package ru.itmo.roguelike.characters.projectiles;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.characters.movement.MoverDrunkStraight;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.Tile;
import ru.itmo.roguelike.field.TileType;
import ru.itmo.roguelike.render.particles.Splash;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;
import java.util.Optional;

import static ru.itmo.roguelike.utils.MathUtils.getRandomDouble;

/**
 * Magic ability which only Player can use
 */
public class Fireball extends Projectile {
    private static final int SPEED = 10;
    private Player actor;
    private int lifeTime = 0;

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
            damage *= ((Player) actor).getLevel() * getRandomDouble(0.7f, 2.f);
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
    public void die() {
        super.die();
        new Splash(position, 1, drawableDescriptor.getColor());
    }

    @Override
    public void act(Field field) {
        lifeTime++;
        if (lifeTime % 5 == 0) {
            new Splash(position, 1, Color.YELLOW, time -> (time % 2) * (5 - Math.abs(time - 5)));
        }
        for (int i = -10; i < 11; i += 10) {
            for (int j = -10; j < 11; j += 10) {
                IntCoordinate pos = new IntCoordinate(position.getX() + i, position.getY() + j);
                Optional<Tile> t = field.getTile(pos);
                if (t.isPresent() && t.get().getType() == TileType.ROCK) {
                    new Splash(pos, 12, Color.BLACK);
                    t.get().reInit(0.5f);
                    die();
                }
            }
        }
        IntCoordinate delta = new IntCoordinate(direction);
        delta.mult(SPEED);
        position = mover.move(position, delta);
    }

    @Override
    public Shape getShape() {
        return new Rectangle(-4, -4, 18, 18);
    }

    public void setPosition(IntCoordinate position) {
        this.position = new IntCoordinate(position);
    }
}
