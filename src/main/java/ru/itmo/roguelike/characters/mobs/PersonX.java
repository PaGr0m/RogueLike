package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import static ru.itmo.roguelike.utils.MathUtils.getRandomDouble;

/**
 * Like a boss on level
 */
public class PersonX extends Enemy {
    private static final float MIN_BOUND_XP = 3;
    private static final float MAX_BOUND_XP = 5;

    public PersonX() {
        this.maxHp = 30;
    }

    public PersonX(Actor target) {
        super(target);
    }

    @Override
    protected float getXPInBounds() {
        return (float) getRandomDouble(MIN_BOUND_XP, MAX_BOUND_XP);
    }

}
