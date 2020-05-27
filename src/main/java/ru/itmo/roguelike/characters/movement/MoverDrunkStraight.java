package ru.itmo.roguelike.characters.movement;

import java.util.Random;

/**
 * Straight line movement with random side deviations
 */
public class MoverDrunkStraight extends Mover {
    private final int VARY_VALUE = 3;
    private final Random random = new Random();

    @Override
    public int moveX(int oldX, int deltaX) {
        return super.moveX(oldX, deltaX) + random.nextInt(VARY_VALUE * 2 + 1) - VARY_VALUE;
    }

    @Override
    public int moveY(int oldY, int deltaY) {
        return super.moveY(oldY, deltaY) + random.nextInt(VARY_VALUE * 2 + 1) - VARY_VALUE;
    }
}
