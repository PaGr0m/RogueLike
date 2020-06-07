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
        if (getTarget() == null) {
            return IntCoordinate.getZeroPosition();
        }

        return null;
    }
}
