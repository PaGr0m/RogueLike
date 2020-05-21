package ru.itmo.roguelike.characters.movement;

public class Mover {
    public Mover() { }

    public Mover getWrapped(Class<?> effect) {
        return this;
    }

    public int moveX(int oldX, int deltaX) {
        return oldX + deltaX;
    }

    public int moveY(int oldY, int deltaY) {
        return oldY + deltaY;
    }
}
