package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.movement.MoverEmbarrassment;

import java.awt.*;

import static ru.itmo.roguelike.utils.MathUtils.getRandomDouble;

/**
 * Моб-пузырь
 */
public class Slime extends Enemy {
    private static final float MIN_BOUND_XP = 0;
    private static final float MAX_BOUND_XP = 1;

    {
        drawableDescriptor.setColor(new Color(0x5900FF));
    }

    public Slime() {
    }

    @Override
    public void collide(Collidable c) {
        if (c instanceof Player) {
            ((Player) c).activateMoveEffect(MoverEmbarrassment.class, 100);
        }
        super.collide(c);
    }

    public Slime(Actor target) {
        super(target);
    }

    @Override
    protected float getXPInBounds() {
        return (float) getRandomDouble(MIN_BOUND_XP, MAX_BOUND_XP);
    }

}
