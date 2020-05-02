package ru.itmo.roguelike.characters;

import ru.itmo.roguelike.Collidable;

import java.awt.*;

public class Player extends Actor {
    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0xFF0000));
    }

    @Override
    public void collide(Collidable c) {

    }

    //TODO: Add this method
    @Override
    public void die() {

    }
}
