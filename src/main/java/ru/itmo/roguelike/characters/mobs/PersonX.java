package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import static ru.itmo.roguelike.utils.MathUtils.getRandomDouble;

/**
 * Like a boss on level
 */
public class PersonX extends Enemy {
    private static float MIN_BOUND = 3;
    private static float MAX_BOUND = 5;

    public PersonX() {
    }

    public PersonX(Actor target) {
        super(target);
    }

    @Override
    protected float getExp() {
        return (float) getRandomDouble(MIN_BOUND, MAX_BOUND);
    }

}
