package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.utils.Coordinate;
import ru.itmo.roguelike.utils.Pair;

import java.util.Optional;

/**
 * Mobs do nothing if they see player
 */
public class PassiveBehavior implements MobBehavior {

    /**
     * Specifying mobs step according to behaviour
     * Stay on the same place
     *
     * @return next mob step, according to behaviour
     */
    @Override
    public Optional<Coordinate> getPath() {
        return Optional.of(new Coordinate(0, 0));
    }
}
