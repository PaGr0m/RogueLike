package ru.itmo.roguelike.characters;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.map.Tile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;

public class Player extends Actor {
    private int momentumX, momentumY;

    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0xFF0000));
    }

    @Override
    public void collide(Collidable c) {
        if (c instanceof Tile) {
            if (((Tile) c).isSolid()) {
                positionX -= momentumX;
                positionY -= momentumY;
            }
        }
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
        momentumX = dx;
        momentumY = dy;
    }

}
