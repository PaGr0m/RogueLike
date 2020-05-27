package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.movement.Mover;
import ru.itmo.roguelike.characters.movement.MoverEmbarrassment;
import ru.itmo.roguelike.settings.GameSettings;
import ru.itmo.roguelike.utils.Pair;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class RandomWalkBehavior implements MobWithTarget {
    private Actor self;

    private static final Map<TurnTo, Integer> randomMoves = new EnumMap<>(TurnTo.class);

    static {
        randomMoves.put(TurnTo.TO_LEFT, -GameSettings.STEP);
        randomMoves.put(TurnTo.IN_PLACE, 0);
        randomMoves.put(TurnTo.TO_RIGHT, GameSettings.STEP);
    }

    @Override
    public Pair<Integer, Integer> getPath() {
        self.setX(self.getX() + getRandomMove());
        self.setY(self.getY() + getRandomMove());

        return new Pair<>(
                Integer.signum(self.getX() + getRandomMove()),
                Integer.signum(self.getY() + getRandomMove())
        );
    }

    private int getRandomMove() {
        Random random = new Random();
        return randomMoves.get(TurnTo.values()[random.nextInt(randomMoves.size())]);
    }

    @Override
    public void setSelf(Actor self) {
        this.self = self;
    }

    @Override
    public void setTarget(Actor target) {
    }

    private enum TurnTo {
        TO_LEFT,
        IN_PLACE,
        TO_RIGHT,
        ;
    }
}
