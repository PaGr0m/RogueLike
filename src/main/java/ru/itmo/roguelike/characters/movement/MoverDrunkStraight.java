package ru.itmo.roguelike.characters.movement;

import ru.itmo.roguelike.utils.IntCoordinate;

import java.util.Random;

/**
 * Straight line movement with random side deviations
 */
public class MoverDrunkStraight extends Mover {
    private final int VARY_VALUE = 3;
    private final Random random = new Random();

    @Override
    public IntCoordinate move(IntCoordinate origin, IntCoordinate delta) {
        int dx = random.nextInt(VARY_VALUE * 2 + 1) - VARY_VALUE;
        int dy = random.nextInt(VARY_VALUE * 2 + 1) - VARY_VALUE;
        delta.add(new IntCoordinate(dx, dy));
        return super.move(origin, delta);
    }
}
