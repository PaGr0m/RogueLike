package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.utils.Coordinate;
import ru.itmo.roguelike.utils.Pair;

import java.util.Optional;

/**
 * Mobs with aggressive behaviour fights with player
 */
public class AggressiveBehavior implements MobWithTarget {
    private Actor self;
    private Actor target;

    /**
     * Specifying mobs step according to behaviour
     * Try to catch player
     *
     * @return next mob step, according to behaviour
     */
    @Override
    public Optional<Coordinate> getPath() {
        if (target == null) {
            return Optional.empty();
        }

        final int dx = target.getX() - self.getX();
        final int dy = target.getY() - self.getY();
        if (dx * dx + dy * dy < self.getRadius()) {
            return Optional.of(new Coordinate(Integer.signum(dx), Integer.signum(dy)));
        }

        return Optional.of(new Coordinate(0, 0));
    }

    public void setTarget(Actor target) {
        this.target = target;
    }

    @Override
    public void setSelf(Actor self) {
        this.self = self;
    }
}
