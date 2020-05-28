package ru.itmo.roguelike.characters.movement;

import ru.itmo.roguelike.settings.GameSettings;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

/**
 * Класс определяющий поведение движения
 * персонажа под действием заклинания "Конфузия
 * <p>
 * Pattern Decorator
 */
public class MoverEmbarrassment extends Mover {
    private static final Map<TurnTo, Integer> randomMoves = new EnumMap<>(TurnTo.class);

    static {
        randomMoves.put(TurnTo.TO_LEFT, -GameSettings.STEP);
        randomMoves.put(TurnTo.IN_PLACE, 0);
        randomMoves.put(TurnTo.TO_RIGHT, GameSettings.STEP);
    }

    private Mover wrapped;

    public MoverEmbarrassment(Mover wrapped) {
        this.wrapped = wrapped;
    }

    /**
     * Удалить эффект передвижения
     *
     * @param effect - эффект передвижения, который необходимо удалить
     * @return цепочка эффектов (в качестве задекорированных классов),
     * которые были применены, без текущего effect
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
     * Под заклинанием "Конфузия" персонаж может перемещаться
     * в одном из 3 ближайших направлений, от изначального
     *
     * @return значение на которое перемещается координата
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
