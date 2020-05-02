package ru.itmo.roguelike.characters;

import ru.itmo.roguelike.Collidable;

import java.awt.*;

public class Player extends Actor {
    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0xFF0000));
    }

    public void go(int dx, int dy) {
        positionX += dx;
        positionY += dy;
    }

    @Override
    public void collide(Collidable c) {

    }
}
