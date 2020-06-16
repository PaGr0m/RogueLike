package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.IntCoordinate;

public class BossBehavior extends MobWithTarget {
    private final Field field;

    public BossBehavior(Field field) {
        this.field = field;
    }

    @Override
    public IntCoordinate getPath() {
        if (target == null) {
            return IntCoordinate.getZeroPosition();
        }

        final IntCoordinate diff = new IntCoordinate(target.getPosition());
        diff.substract(self.getPosition());
        if (diff.lenL2() < self.getRadius()) {
            return diff.signum();
        }

        return IntCoordinate.getZeroPosition();
    }
}
