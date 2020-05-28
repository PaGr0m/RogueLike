package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import static ru.itmo.roguelike.utils.Math.getRandomNumber;

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
        return (float) getRandomNumber(MIN_BOUND_EXP, MAX_BOUND_EXP);
    }

}
