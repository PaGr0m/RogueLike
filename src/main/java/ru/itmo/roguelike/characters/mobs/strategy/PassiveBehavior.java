package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.utils.IntCoordinate;

import java.util.Optional;

public class PassiveBehavior implements MobBehavior {
    @Override
    public Optional<IntCoordinate> getPath() {
        return Optional.of(new IntCoordinate(0, 0));
    }
}
