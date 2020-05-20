package ru.itmo.roguelike.characters;

import ru.itmo.roguelike.Collidable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;

public class Player extends Actor {
    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0xFF0000));
    }

    @Override
    public void collide(Collidable c) {

    }

    @Override
    public void go() {
        throw new NotImplementedException();
    }

    //TODO: Add this method
    @Override
    public void die() {

    }

    public void go(int dx, int dy) {
        positionX += dx;
        positionY += dy;
    }
}
