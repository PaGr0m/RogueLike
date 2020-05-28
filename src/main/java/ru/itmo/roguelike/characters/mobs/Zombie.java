package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import java.awt.*;

public class Zombie extends Enemy {
    private static final float MIN_BOUND_EXP = 1;
    private static final float MAX_BOUND_EXP = 3;

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
        return (float) (Math.random() * (MAX_BOUND_EXP - MIN_BOUND_EXP) + MIN_BOUND_EXP);
    }


}
