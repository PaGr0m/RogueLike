package ru.itmo.roguelike.characters;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.movement.Mover;
import ru.itmo.roguelike.map.Tile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.UnaryOperator;

/**
 * Player class
 */
public class Player extends Actor {
    private Mover mover = new Mover();
    private final Queue<Integer> previousPosX = new LinkedList<>();
    private final Queue<Integer> previousPosY = new LinkedList<>();

    private int currCollisionIteration = 0;
    private int collisionIteration = 0;

    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX)
                .setY(this.positionY)
                .setColor(new Color(0xFF0000));
    }

    //TODO: Add collide for other
    @Override
    public void collide(Collidable c) {
        if (c instanceof Tile) {
            if (((Tile) c).isSolid()) {
                if (!previousPosX.isEmpty()) {
                    positionX = previousPosX.remove();
                }
                if (!previousPosY.isEmpty()) {
                    positionY = previousPosY.remove();
                }
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

    /**
     * Make step
     *
     * @param dx --- difference on x coordinate
     * @param dy --- difference on y coordinate
     */
    public void go(int dx, int dy) {
        if (collisionIteration != currCollisionIteration) {
            previousPosX.clear();
            previousPosY.clear();
            currCollisionIteration = collisionIteration;
        }
        previousPosX.add(positionX);
        previousPosY.add(positionY);

        positionX = mover.moveX(positionX, dx);
        positionY = mover.moveY(positionY, dy);
    }

}
