package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import java.awt.*;

import static ru.itmo.roguelike.utils.MathUtils.getRandomDouble;

/**
 * Моб-пузырь
 */
public class Slime extends Enemy {
    private static float MIN_BOUND = 0;
    private static float MAX_BOUND = 1;

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
        return (float) getRandomDouble(MIN_BOUND, MAX_BOUND);
    }

}
