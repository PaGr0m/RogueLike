package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.utils.IntCoordinate;

public class CowardlyBehavior extends AggressiveBehavior {
    @Override
    public IntCoordinate getPath() {
        return super.getPath().inverse();
    }
}
