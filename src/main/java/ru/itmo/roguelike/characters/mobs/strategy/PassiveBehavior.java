package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.utils.Coordinate;
import ru.itmo.roguelike.utils.Pair;

public class PassiveBehavior implements MobBehavior {
    @Override
    public Coordinate getPath() {
        return new Coordinate(0, 0);
    }
}
