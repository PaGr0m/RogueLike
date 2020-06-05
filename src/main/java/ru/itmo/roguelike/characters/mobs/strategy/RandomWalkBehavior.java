package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.utils.IntCoordinate;
import ru.itmo.roguelike.utils.MathUtils;

import java.util.Random;

public class RandomWalkBehavior extends MobWithTarget {
    private static final Random random = new Random();
    private static final float PROBABILITY = 0.3f;
    private static final int STEP = 5;
    private IntCoordinate delta = IntCoordinate.getZeroPosition();

    @Override
    public IntCoordinate getPath() {
        final IntCoordinate diff = new IntCoordinate(target.getPosition());
        diff.substract(self.getPosition());
        if (diff.lenL2() < self.getRadius()) {
            return diff.signum();
        }

        if (delta.equals(IntCoordinate.getZeroPosition()) ||
                self.getLastPosition().equals(self.getPosition())) {
            delta = new IntCoordinate(MathUtils.getRandomInt(-STEP, STEP),
                    MathUtils.getRandomInt(-STEP, STEP));
        }

        IntCoordinate nextCoordinate = delta.signum().inverse();

        if (random.nextFloat() < PROBABILITY) {
            nextCoordinate.setX(0);
        }
        if (random.nextFloat() < PROBABILITY) {
            nextCoordinate.setY(0);
        }

        delta.add(nextCoordinate);

        return nextCoordinate;
    }
}
