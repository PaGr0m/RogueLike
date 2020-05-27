package ru.itmo.roguelike.characters.movement;


import ru.itmo.roguelike.utils.IntCoordinate;

/**
 * Класс определяющий поведение движения персонажа
 */
public class Mover {
    public Mover() {
    }

    private final IntCoordinate lastMove = IntCoordinate.getZeroPosition();

    public Mover removeEffect(Class<?> effect) {
        return this;
    }

    public IntCoordinate move(IntCoordinate origin, IntCoordinate delta) {
        lastMove.setXY(origin);
        IntCoordinate newCoord = new IntCoordinate(origin);
        newCoord.add(delta);
        return newCoord;
    }

    public IntCoordinate getLastMove() {
        return lastMove;
    }
}
