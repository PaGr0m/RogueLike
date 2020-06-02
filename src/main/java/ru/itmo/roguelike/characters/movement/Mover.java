package ru.itmo.roguelike.characters.movement;


import ru.itmo.roguelike.utils.IntCoordinate;

/**
 * Класс определяющий поведение движения персонажа
 */
public class Mover {
    private final IntCoordinate lastMove = IntCoordinate.getZeroPosition();

    public Mover() {
    }

    public Mover removeEffect(Class<? extends Mover> effect) {
        return this;
    }

    public IntCoordinate move(IntCoordinate origin, IntCoordinate delta) {
        lastMove.set(origin);
        IntCoordinate newCoord = new IntCoordinate(origin);
        newCoord.add(delta);
        return newCoord;
    }

    public IntCoordinate getLastMove() {
        return lastMove;
    }

    public boolean contains(Class<? extends Mover> effect) {
        return effect.equals(Mover.class);
    }

    public IntCoordinate getLastPosition() {
        return lastMove;
    }
}
