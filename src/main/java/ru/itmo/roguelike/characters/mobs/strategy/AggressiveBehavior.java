package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.utils.Pair;

public class AggressiveBehavior implements MobWithTarget {
    private Actor self;
    private Actor target;

    @Override
    public Pair<Integer, Integer> getPath() {
        if (target == null) {
            return new Pair<>(0, 0);
        }

        final int dx = target.getX() - self.getX();
        final int dy = target.getY() - self.getY();
        if (dx * dx + dy * dy < self.getRadius()) {
            return new Pair<>(Integer.signum(dx), Integer.signum(dy));
        }

        return new Pair<>(0, 0);
    }

    public void setTarget(Actor target) {
        this.target = target;
    }

    @Override
    public void setSelf(Actor self) {
        this.self = self;
    }
}
