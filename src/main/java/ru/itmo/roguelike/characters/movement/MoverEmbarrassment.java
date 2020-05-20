package ru.itmo.roguelike.characters.movement;

import ru.itmo.roguelike.settings.GameSettings;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;


// TODO: rename variables
// TODO: magic number to step constant
public class MoverEmbarrassment extends Mover {
    private final Mover wrapped;

    private final Map<MoveTo, Integer> moveMap = new EnumMap<>(MoveTo.class);

    {
        moveMap.put(MoveTo.TO_LEFT, -GameSettings.STEP);
        moveMap.put(MoveTo.IN_PLACE, 0);
        moveMap.put(MoveTo.TO_RIGHT, GameSettings.STEP);
    }

    public MoverEmbarrassment(Mover wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public Mover getWrapped() {
        return wrapped;
    }

    public Mover getWrapped(Class<?> effect) {
        if (this.getClass().equals(effect)) {
            return wrapped;
        }

        return super.getWrapped();
    }

    @Override
    public int moveX(int oldX, int deltaX) {
        if (deltaX == 0) {
            Random random = new Random();
            deltaX = moveMap.get(MoveTo.values()[random.nextInt(MoveTo.size())]);
        }

        return super.moveX(oldX, deltaX);
    }

    @Override
    public int moveY(int oldY, int deltaY) {
        if (deltaY == 0) {
            Random random = new Random();
            deltaY = moveMap.get(MoveTo.values()[random.nextInt(MoveTo.size())]);
        }

        return super.moveY(oldY, deltaY);
    }

    // TODO: rename
    private enum MoveTo {
        TO_LEFT,
        IN_PLACE,
        TO_RIGHT,
        ;

        public static int size() {
            return values().length;
        }
    }
}
