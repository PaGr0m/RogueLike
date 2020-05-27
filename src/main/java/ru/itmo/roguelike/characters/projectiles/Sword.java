package ru.itmo.roguelike.characters.projectiles;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.manager.gamemanager.GameManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class Sword extends Projectile {
    private int ttl = 10;
    private static final Shape shape = new java.awt.Rectangle(2, 40);

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
            ((Enemy) c).strike(this.damage);
        }
    }

    private static AffineTransform addGlobalRotation(AffineTransform transform) {
        transform.rotate(GameManager.GLOBAL_TIME / 2.);
        return transform;
    }

    @Override
    public AffineTransform getAdditionalTransform() {
        return addGlobalRotation(new AffineTransform());
    }

    @Override
    public Shape getShape() {
        return shape;
    }
}
