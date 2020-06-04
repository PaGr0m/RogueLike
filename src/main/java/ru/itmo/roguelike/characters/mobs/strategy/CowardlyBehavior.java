package ru.itmo.roguelike.characters.mobs.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.utils.IntCoordinate;

public class CowardlyBehavior implements MobWithTarget {
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
            return diff.signum().inverse();
        }

        return IntCoordinate.getZeroPosition();
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
