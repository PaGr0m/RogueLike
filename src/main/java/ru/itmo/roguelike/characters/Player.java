package ru.itmo.roguelike.characters;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.map.Tile;
import ru.itmo.roguelike.characters.movement.Mover;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.util.function.UnaryOperator;

public class Player extends Actor {
    private Mover mover;
    private int previousPosX;
    private int previousPosY;

    private int currCollisionIteration = 0;
    private int collisionIteration = 0;

    public Player() {
        this.mover = new Mover();

        previousPosX = positionX;
        previousPosY = positionY;
    }

    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX)
                          .setY(this.positionY)
                          .setColor(new Color(0xFF0000));
    }

    @Override
    public void collide(Collidable c) {
        if (c instanceof Tile) {
            if (((Tile) c).isSolid()) {
                positionX = previousPosX;
                positionY = previousPosY;
            }
        }
        collisionIteration++;
    }

    @Override
    public void go() {
        throw new NotImplementedException();
    }

    //TODO: Add this method
    @Override
    public void die() {

    }

    public void activateMoveEffect(@NotNull UnaryOperator<Mover> modifier) {
        mover = modifier.apply(mover);
    }

    public void deactivateMoveEffect(Class<?> effect) {
        mover = mover.removeEffect(effect);
    }

    public void go(int dx, int dy) {
        if (collisionIteration != currCollisionIteration) {
            previousPosX = positionX;
            previousPosY = positionY;
            currCollisionIteration = collisionIteration;
        }
        positionX = mover.moveX(positionX, dx);
        positionY = mover.moveY(positionY, dy);
    }

}
