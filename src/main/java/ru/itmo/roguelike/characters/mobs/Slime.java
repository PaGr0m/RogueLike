package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

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

    public Slime(Actor target) {
        super(target);
    }

    @Override
    protected float getXPInBounds() {
        return (float) getRandomDouble(MIN_BOUND_XP, MAX_BOUND_XP);
    }

}
