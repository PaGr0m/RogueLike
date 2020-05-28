package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import java.awt.*;

import static ru.itmo.roguelike.utils.Math.getRandomNumber;

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
        return (float) getRandomNumber(MIN_BOUND_EXP, MAX_BOUND_EXP);
    }


}
