package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.utils.Pair;

public class CowardlyBehavior implements MobWithTarget {
    private final Actor self;
    private Actor target;

    private final float radius;

    public CowardlyBehavior(Actor self, float radius) {
        this.self = self;
        this.radius = radius;
    }

    @Override
    public Pair<Integer, Integer> getPath() {
        if (target == null) {
            return new Pair<>(0, 0);
        }

        final int dx = self.getX() - target.getX();
        final int dy = self.getY() - target.getY();
        if (dx * dx + dy * dy < radius) {
            return new Pair<>(Integer.signum(dx), Integer.signum(dy));
        }

        return new Pair<>(0, 0);
    }

    public void setTarget(Actor target) {
        this.target = target;
    }
}
