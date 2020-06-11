package ru.itmo.roguelike.characters.movement;

import ru.itmo.roguelike.utils.IntCoordinate;
import ru.itmo.roguelike.utils.MathUtils;

/**
 * Straight line movement with random side deviations
 */
public class MoverDrunkStraight extends Mover {
    private static final int VARY_VALUE = 3;

    @Override
    public IntCoordinate move(IntCoordinate origin, IntCoordinate delta) {
        int dx = MathUtils.getRandomInt(-VARY_VALUE, VARY_VALUE);
        int dy = MathUtils.getRandomInt(-VARY_VALUE, VARY_VALUE);
        delta.add(new IntCoordinate(dx, dy));
        return super.move(origin, delta);
    }

    @Override
    public boolean contains(Class<? extends Mover> effect) {
        if (effect.equals(MoverDrunkStraight.class)) {
            return true;
        }
        return super.contains(effect);
    }
}
