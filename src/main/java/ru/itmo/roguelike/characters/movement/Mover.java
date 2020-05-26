package ru.itmo.roguelike.characters.movement;

/**
 * Класс определяющий поведение движения персонажа
 */
public class Mover {
    public Mover() {
    }

    public Mover removeEffect(Class<?> effect) {
        return this;
    }

    public int moveX(int oldX, int deltaX) {
        return oldX + deltaX;
    }

    public int moveY(int oldY, int deltaY) {
        return oldY + deltaY;
    }
}
