package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.utils.Pair;

public class AggressiveBehavior implements MobWithTarget {
    private final Actor self;
    private Actor target;

    private final float radius;

    public AggressiveBehavior(Actor self, float radius) {
        this.self = self;
        this.radius = radius;
    }

    @Override
    public Pair<Integer, Integer> getPath() {
        if (target == null) {
            return new Pair<>(0, 0);
        }

        final int dx = self.getPositionX() - target.getPositionX();
        final int dy = self.getPositionY() - target.getPositionY();
        if (dx * dx + dy * dy < radius) {
            return new Pair<>(
                    Integer.signum(target.getPositionX() - self.getPositionX()),
                    Integer.signum(target.getPositionY() - self.getPositionY())
            );
        }

        return new Pair<>(0, 0);
    }

    public void setTarget(Actor target) {
        this.target = target;
    }
}
