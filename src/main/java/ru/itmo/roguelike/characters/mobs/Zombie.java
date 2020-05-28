package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import java.awt.*;

import static ru.itmo.roguelike.utils.MathUtils.getRandomDouble;

public class Zombie extends Enemy {
    private static float MIN_BOUND_XP = 1;
    private static float MAX_BOUND_XP = 3;

    {
        drawableDescriptor.setColor(Color.CYAN);
    }

    public Zombie() {
        super();
    }

    public Zombie(Actor target) {
        super(target);
    }

    @Override
    protected float getXPInBounds() {
        return (float) getRandomDouble(MIN_BOUND_XP, MAX_BOUND_XP);
    }
}
