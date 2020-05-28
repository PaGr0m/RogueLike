package ru.itmo.roguelike.characters.projectiles;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.manager.gamemanager.GameManager;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * The sword.
 * Constantly rotates using global step counter (aka {@link GameManager#GLOBAL_TIME})
 * to calculate the angle. self-destructs when it's time to life (aka ttl) reaches 0
 * NB: time is measured as number of `act()` calls
 */
public class Sword extends Projectile {
    private static final Shape shape = new java.awt.Rectangle(2, 40);
    private int ttl = 10;
    private Player player;

    {
        damage = 10;
    }

    public Sword() {
        super((graphics, x, y) -> {
            AffineTransform transform = new AffineTransform();
            transform.translate(x, y);
            graphics.draw(Sword.addGlobalRotation(transform).createTransformedShape(shape));
        });
        drawableDescriptor.setColor(Color.pink);
    }

    //TODO: Code duplicates
    public Sword(Player player) {
        super((graphics, x, y) -> {
            AffineTransform transform = new AffineTransform();
            transform.translate(x, y);
            graphics.draw(Sword.addGlobalRotation(transform).createTransformedShape(shape));
        });
        drawableDescriptor.setColor(Color.pink);
        this.player = player;
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
            ((Enemy) c).strike(this.damage, player);
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
        return shape;
    }
}
