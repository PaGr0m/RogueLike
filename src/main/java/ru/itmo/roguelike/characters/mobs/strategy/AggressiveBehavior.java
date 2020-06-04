package ru.itmo.roguelike.characters.mobs.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.utils.IntCoordinate;

public class AggressiveBehavior implements MobWithTarget {
    private Actor self;
    private Actor target;

    @Override
    public IntCoordinate getPath() {
        if (target == null) {
            return IntCoordinate.getZeroPosition();
        }

        final IntCoordinate diff = new IntCoordinate(target.getPosition());
        diff.substract(self.getPosition());
        if (diff.lenL2() < self.getRadius()) {
            return diff.signum();
        }

        return IntCoordinate.getZeroPosition();
    }

    public void setTarget(@NotNull Actor target) {
        this.target = target;
    }

    @Override
    public @NotNull Actor getSelf() {
        return self;
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
