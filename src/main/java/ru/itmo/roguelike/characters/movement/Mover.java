package ru.itmo.roguelike.characters.movement;

/**
 * Класс определяющий поведение движения персонажа
 */
public class Mover {
    public Mover() { }

    public Mover removeEffect(Class<?> effect) {
        return this;
    }

    public float moveX(float oldX, float deltaX) {
        return oldX + deltaX;
    }

    public float moveY(float oldY, float deltaY) {
        return oldY + deltaY;
    }
}
