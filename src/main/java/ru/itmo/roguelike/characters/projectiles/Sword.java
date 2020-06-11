package ru.itmo.roguelike.characters.projectiles;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.manager.gamemanager.GameManager;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static ru.itmo.roguelike.utils.MathUtils.getRandomDouble;

/**
 * The sword.
 * Constantly rotates using global step counter (aka {@link GameManager#GLOBAL_TIME})
 * to calculate the angle. self-destructs when it's time to life (aka ttl) reaches 0
 * NB: time is measured as number of {@code act()} calls
 */
public class Sword extends Projectile {
    private static final Shape GRAPH_SHAPE = new java.awt.Rectangle(2, 40);
    private static final Shape PHYS_SHAPE = new java.awt.Rectangle(12, 55);
    private int ttl = 10;
    private Player actor;

    {
        damage = 8;
    }

    public Sword(Actor actor) {
        super((graphics, x, y) -> {
            AffineTransform transform = new AffineTransform();
            transform.translate(x, y);
            graphics.fill(Sword.addGlobalRotation(transform).createTransformedShape(GRAPH_SHAPE));
        });
        drawableDescriptor.setColor(Color.ORANGE);
        if (actor instanceof Player) {
            this.actor = (Player) actor;
            //increase damage according to player level
            damage *= ((Player) actor).getLevel() * getRandomDouble(1.f, 1.5f);
        }
    }

    /**
     * Adds sword rotation to current affine transformation
     *
     * @param transform -- current transform
     * @return transform with rotation
     */
    private static AffineTransform addGlobalRotation(AffineTransform transform) {
        transform.rotate(GameManager.GLOBAL_TIME / 2.);
        return transform;
    }

    @Override
    public void act(Field field) {
        ttl--;
        if (ttl == 0) {
            die();
        }
    }

    @Override
    public void collide(Collidable c) {
        if (c instanceof Enemy) {
            ((Enemy) c).strike(this.damage, actor);
        }
    }

    /**
     * @return current sword rotation
     */
    @Override
    public AffineTransform getAdditionalTransform() {
        return addGlobalRotation(new AffineTransform());
    }

    @Override
    public Shape getShape() {
        return PHYS_SHAPE;
    }
}
