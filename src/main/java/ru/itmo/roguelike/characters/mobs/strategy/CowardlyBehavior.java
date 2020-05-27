package ru.itmo.roguelike.characters.mobs.strategy;

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

        final int dx = target.getPosition().getX() - self.getPosition().getY();
        final int dy = target.getPosition().getY() - self.getPosition().getY();
        if (dx * dx + dy * dy < self.getRadius()) {
            return new IntCoordinate(Integer.signum(dx), Integer.signum(dy));
        }

        return IntCoordinate.getZeroPosition();
    }

    public void setTarget(Actor target) {
        this.target = target;
    }

    @Override
    public void setSelf(Actor self) {
        this.self = self;
    }
}
