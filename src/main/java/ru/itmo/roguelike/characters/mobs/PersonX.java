package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

/**
 * Like a boss on level
 */
public class PersonX extends Enemy {
    private static final float MIN_BOUND_EXP = 3;
    private static final float MAX_BOUND_EXP = 5;
    public PersonX() {
    }

    public PersonX(Actor target) {
        super(target);
    }

    @Override
    protected float getExp() {
        return (float) (Math.random() * (MAX_BOUND_EXP - MIN_BOUND_EXP) + MIN_BOUND_EXP);
    }

}
