package ru.itmo.roguelike.characters;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.movement.Mover;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.TileType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.util.function.UnaryOperator;

import static ru.itmo.roguelike.field.TileType.WATER;

public class Player extends Actor {
    private Mover mover = new Mover();

    public Player() {
        drawableDescriptor.setColor(Color.RED);
    }

    @Override
    public void collide(Collidable c) {

    }

    @Override
    public void go(Field field) {
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

    public void go(int dx, int dy, Field field) {
        TileType currTile = field.getTileType(positionX, positionY);

        if (currTile == WATER) {
            dx /= 2;
            dy /= 2;
        }

        goTo(mover.moveX(positionX, dx), mover.moveY(positionY, dy), field);
    }

}
