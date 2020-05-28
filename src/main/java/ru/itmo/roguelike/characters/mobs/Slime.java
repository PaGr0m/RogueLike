package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import java.awt.*;

import static ru.itmo.roguelike.utils.Math.getRandomNumber;

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
        return (float) getRandomNumber(MIN_BOUND_EXP, MAX_BOUND_EXP);
    }


}
