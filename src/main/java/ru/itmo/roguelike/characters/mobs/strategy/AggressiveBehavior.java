package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.util.Optional;

public class AggressiveBehavior implements MobWithTarget {
    private Actor self;
    private Actor target;

    @Override
    public Optional<IntCoordinate> getPath() {
        if (target == null) {
            return Optional.empty();
        }

        final int dx = target.getPosition().getX() - self.getPosition().getY();
        final int dy = target.getPosition().getY() - self.getPosition().getY();
        if (dx * dx + dy * dy < self.getRadius()) {
            return Optional.of(new IntCoordinate(Integer.signum(dx), Integer.signum(dy)));
        }

        return Optional.of(new IntCoordinate(0, 0));
    }

    public void setTarget(Actor target) {
        this.target = target;
    }

    @Override
    public void setSelf(Actor self) {
        this.self = self;
    }
}
