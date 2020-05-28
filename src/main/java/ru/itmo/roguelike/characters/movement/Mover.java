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

    public int moveX(int oldX, int deltaX) {
        lastMove.setX(oldX);
        return oldX + deltaX;
    }

    public int moveY(int oldY, int deltaY) {
        lastMove.setY(oldY);
        return oldY + deltaY;
    }

    public int getLastX() {
        return lastMove.getX();
    }

    public int getLastY() {
        return lastMove.getY();
    }

    public IntCoordinate getLastPosition() {
        return lastMove;
    }
}
