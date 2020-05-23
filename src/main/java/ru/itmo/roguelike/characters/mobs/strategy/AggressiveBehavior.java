package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.utils.Coordinate;
import ru.itmo.roguelike.utils.Pair;

public class AggressiveBehavior implements MobWithTarget {
    private Actor self;
    private Actor target;

    @Override
    public Coordinate getPath() {
        if (target == null) {
            return new Coordinate(0, 0);
        }

        final float dx = target.getCoordinate().getX() - self.getCoordinate().getX();
        final float dy = target.getCoordinate().getY() - self.getCoordinate().getY();
        if (dx * dx + dy * dy < self.getRadius()) {
//            return new Pair<>(Integer.signum(dx), Integer.signum(dy));
            return new Coordinate(Integer.signum((int) dx), Integer.signum((int) dy)); // TODO: доделать
        }

        return new Coordinate(0, 0);
    }

    public void setTarget(Actor target) {
        this.target = target;
    }

    @Override
    public void setSelf(Actor self) {
        this.self = self;
    }
}
