package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;

import java.awt.*;
import java.util.Random;

import static ru.itmo.roguelike.utils.MathUtils.getRandomDouble;

/**
 * Like a boss on level
 */
public class PersonX extends Enemy implements Boss {
    private static final float MIN_BOUND_XP = 40;
    private static final float MAX_BOUND_XP = 70;
    private static final Random random = new Random();

    {
        drawableDescriptor.setColor(Color.PINK);
        damage = 15;
    }

    public PersonX() {
        super((graphics, x, y) -> {
            graphics.setColor(Color.BLACK);
            graphics.fillRoundRect(x, y, 32, 32, 5, 5);
            graphics.setColor(Color.PINK);
            graphics.fillRoundRect(x + 1, y + 1, 30, 30, 5, 5);
        });
        init(80);
    }

    public PersonX(Actor target) {
        super(target);
    }

    @Override
    protected float getXPInBounds() {
        return (float) getRandomDouble(MIN_BOUND_XP, MAX_BOUND_XP);
    }

    @Override
    public Shape getShape() {
        return new Rectangle(32, 32);
    }

    @Override
    public void collide(Collidable c) {
        // если настигли цель
        if (c.equals(target)) {
            if (random.nextFloat() < 0.1) {
                target.strike(this.damage);
            }
        }

        position.set(mover.getLastMove());
    }
}
