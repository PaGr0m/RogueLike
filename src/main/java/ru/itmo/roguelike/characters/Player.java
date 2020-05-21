package ru.itmo.roguelike.characters;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.movement.Mover;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.util.function.UnaryOperator;

public class Player extends Actor {
    private Mover mover;
    private int momentumX;
    private int momentumY;

    public Player() {
        this.mover = new Mover();
    }

    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX)
                          .setY(this.positionY)
                          .setColor(new Color(0xFF0000));
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

    public void activateMoveEffect(@NotNull UnaryOperator<Mover> modifier) {
        mover = modifier.apply(mover);
    }

    public void deactivateMoveEffect(Class<?> effect) {
        mover = mover.getWrapped(effect);
    }

    public void go(int dx, int dy) {
        positionX = mover.moveX(positionX, dx);
        positionY = mover.moveY(positionY, dy);
        momentumX = dx;
        momentumY = dy;
    }
}
