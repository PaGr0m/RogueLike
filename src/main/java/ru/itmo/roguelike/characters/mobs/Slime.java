package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import java.awt.*;

/**
 * Моб-пузырь
 */
public class Slime extends Enemy {
    private static final float MIN_BOUND_EXP = 0;
    private static final float MAX_BOUND_EXP = 1;

    {
        drawableDescriptor.setColor(new Color(0x5900FF));
    }

    public Slime() {
    }

    public Slime(Actor target) {
        super(target);
    }

    @Override
    protected float getExp() {
        return (float) (Math.random() * (MAX_BOUND_EXP - MIN_BOUND_EXP) + MIN_BOUND_EXP);
    }


}
