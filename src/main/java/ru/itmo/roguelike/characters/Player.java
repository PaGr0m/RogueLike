package ru.itmo.roguelike.characters;

import ru.itmo.roguelike.render.drawable.DrawableDescriptor;

import java.awt.*;

public class Player extends Actor {
    @Override
    public DrawableDescriptor draw() {
        return new DrawableDescriptor().setX(this.positionX).setY(this.positionY).setColor(new Color(0xFF0000));
    }

    public void go(int dx, int dy) {
        positionX += dx;
        positionY += dy;
    }
}
