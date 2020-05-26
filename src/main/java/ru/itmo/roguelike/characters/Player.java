package ru.itmo.roguelike.characters;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.movement.Mover;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.exceptions.DieException;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.TileType;
import ru.itmo.roguelike.utils.Coordinate;
import ru.itmo.roguelike.utils.Pair;

import java.awt.*;
import java.util.Random;
import java.util.function.UnaryOperator;

import static ru.itmo.roguelike.field.TileType.WATER;

public class Player extends Actor {
    private Coordinate moveDirection = Coordinate.zero();
    private Coordinate attackDirection = Coordinate.zero();
    private boolean doAttack = false;

    public Player() {
        drawableDescriptor.setColor(Color.RED);
        init(100);
    }

    @Override
    public void collide(Collidable c) {

    }

    @Override
    public void act(Field field) {
        TileType currTile = field.getTileType(positionX, positionY);

        if (currTile == WATER) {
            moveDirection.div(2);
        }

        go(moveDirection, field);
        if (doAttack && attackDirection.lenL1() > 0) {
            fire(field);
        }

        super.act(field);
        resetState();
    }

    private void resetState() {
        moveDirection = Coordinate.zero();
        attackDirection = Coordinate.zero();
        doAttack = false;
    }

    private void fire(Field field) {
        Fireball fireball = new Fireball(new Pair<>(attackDirection.getX(), attackDirection.getY()));
        fireball.setX(positionX);
        fireball.setY(positionY);
        fireball.act(field);
    }

    private static final Random random = new Random();

    @Override
    public void die() {
        init(
                random.nextInt(1_000_000) - 500_000,
                random.nextInt(1_000_000) - 500_000,
                maxHp
        );
        throw new DieException();
    }

    public void activateMoveEffect(@NotNull UnaryOperator<Mover> modifier) {
        mover = modifier.apply(mover);
    }

    public void deactivateMoveEffect(Class<?> effect) {
        mover = mover.removeEffect(effect);
    }

    public void move(Coordinate by) {
        this.moveDirection.add(by);
    }

    public void attack(Coordinate direction) {
        doAttack = true;
        attackDirection.add(direction);
    }

}
