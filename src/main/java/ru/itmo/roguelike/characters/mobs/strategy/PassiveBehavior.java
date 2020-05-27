package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.utils.IntCoordinate;
import ru.itmo.roguelike.utils.Pair;

public class PassiveBehavior implements MobBehavior {
    @Override
    public IntCoordinate getPath() {
        return IntCoordinate.getZeroPosition();
    }
}
