package ru.itmo.roguelike.characters.mobs.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.utils.Pair;

public class CowardlyBehavior implements MobWithTarget {
    private Actor self;
    private Actor target;

    @Override
    public Pair<Integer, Integer> getPath() {
        if (target == null) {
            return new Pair<>(0, 0);
        }

        final int dx = self.getX() - target.getX();
        final int dy = self.getY() - target.getY();
        if (dx * dx + dy * dy < self.getRadius()) {
            return new Pair<>(Integer.signum(dx), Integer.signum(dy));
        }

        return new Pair<>(0, 0);
    }

    @Override
    public @NotNull Actor getSelf() {
        return self;
    }

    public void setTarget(@NotNull Actor target) {
        this.target = target;
    }

    @Override
    public @NotNull Actor getTarget() {
        return target;
    }

    @Override
    public void setSelf(@NotNull Actor self) {
        this.self = self;
    }
}
