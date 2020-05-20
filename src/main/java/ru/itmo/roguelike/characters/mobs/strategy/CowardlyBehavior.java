package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.utils.Pair;

public class CowardlyBehavior implements MobBehavior {
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

        if (
                (Math.pow(self.getPositionX() - target.getPositionX(), 2)
                        + Math.pow(self.getPositionY() - target.getPositionY(), 2)) < radius
        ) {
            return new Pair<>(
                    Integer.signum(self.getPositionX() - target.getPositionX()),
                    Integer.signum(self.getPositionY() - target.getPositionY())
            );
        }

        return new Pair<>(0, 0);
    }

    public void setTarget(Actor target) {
        this.target = target;
    }
}
