package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.mobs.strategy.AggressiveBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobBehavior;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.FloatCoordinate;
import ru.itmo.roguelike.utils.IntCoordinate;

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
        init(300);
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
    public void act(Field field) {
        IntCoordinate delta = new IntCoordinate(position);
        delta.substract(target.getPosition());

        if (delta.lenL2() <= radius) {
            IntCoordinate lineOfSight = new IntCoordinate(delta);

            direction = FloatCoordinate.fromAngle(lineOfSight.toFloatCoordinate().toAngle()).getSignum(.5f);

            attackMethod.setDirection(direction.inverse());
            attackMethod.attack(field);
            attackMethod.act();
        }

        super.act(field);
    }

    /**
     * If the Boss collides with enemy, he tells him, who does he need to attack
     */
    @Override
    public void collide(Collidable c) {
        if (c instanceof Enemy) {
            final Enemy enemy = (Enemy) c;
            enemy.setBehaviour(MobBehavior.builder(AggressiveBehavior::new).build());
            enemy.setTarget(target);

            position.set(mover.getLastMove());
        }

        // если настигли цель
        if (c.equals(target)) {
            if (random.nextFloat() < 0.1) {
                target.strike(this.damage);
            }
        }

        position.set(mover.getLastMove());
    }
}
