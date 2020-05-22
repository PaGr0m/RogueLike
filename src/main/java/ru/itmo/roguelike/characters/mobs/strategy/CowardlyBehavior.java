package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.utils.Pair;

public class CowardlyBehavior implements MobWithTarget {
    private Actor self;
    private Actor target;

    private float radius;

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

    @Override
    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void setSelf(Actor self) {
        this.self = self;
    }
}
