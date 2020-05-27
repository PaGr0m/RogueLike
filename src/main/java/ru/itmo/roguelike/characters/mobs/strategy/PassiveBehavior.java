package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.utils.IntCoordinate;

public class PassiveBehavior implements MobBehavior {
    @Override
    public IntCoordinate getPath() {
        return IntCoordinate.getZeroPosition();
    }
}
