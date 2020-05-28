package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import java.awt.*;

import static ru.itmo.roguelike.utils.MathUtils.getRandomDouble;

public class Zombie extends Enemy {
    private static float MIN_BOUND = 1;
    private static float MAX_BOUND = 3;

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
    protected float getExp() {
        return (float) getRandomDouble(MIN_BOUND, MAX_BOUND);
    }
}
