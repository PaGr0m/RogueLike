package ru.itmo.roguelike;

public interface Collidable {
    int getX();

    int getY();

    int getWidth();

    int getHeight();

    void collide(Collidable c);
}
