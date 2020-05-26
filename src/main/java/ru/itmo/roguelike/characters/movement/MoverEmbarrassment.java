package ru.itmo.roguelike.characters.movement;

import ru.itmo.roguelike.settings.GameSettings;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

/**
 * Class defining Player's movement under "Confusion" spell
 * <p>
 * Pattern Decorator
 */
public class MoverEmbarrassment extends Mover {
    private Mover wrapped;

    private static final Map<TurnTo, Integer> randomMoves = new EnumMap<>(TurnTo.class);

    static {
        randomMoves.put(TurnTo.TO_LEFT, -GameSettings.STEP);
        randomMoves.put(TurnTo.IN_PLACE, 0);
        randomMoves.put(TurnTo.TO_RIGHT, GameSettings.STEP);
    }

    public MoverEmbarrassment(Mover wrapped) {
        this.wrapped = wrapped;
    }

    /**
     * Remove movement effect
     *
     * @param effect --- movement effect that needs to be removed
     * @return chain of effects (as decorated classes)
     * which was applied without effect
     */
    @Override
    public Mover removeEffect(Class<?> effect) {
        if (this.getClass().equals(effect)) {
            return wrapped;
        }

        wrapped = super.removeEffect(effect);
        return this;
    }

    @Override
    public int moveX(int oldX, int deltaX) {
        if (deltaX == 0) {
            deltaX = getRandomMove();
        }

        return super.moveX(oldX, deltaX);
    }

    @Override
    public int moveY(int oldY, int deltaY) {
        if (deltaY == 0) {
            deltaY = getRandomMove();
        }

        return super.moveY(oldY, deltaY);
    }

    /**
     * Player can move to one of the 3 nearest cells
     * under confusion spell
     *
     * @return value to which Player move
     */
    private int getRandomMove() {
        Random random = new Random();
        return randomMoves.get(TurnTo.values()[random.nextInt(randomMoves.size())]);
    }

    private enum TurnTo {
        TO_LEFT,
        IN_PLACE,
        TO_RIGHT,
        ;
    }
}
