package ru.itmo.roguelike.characters;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.movement.Mover;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.exceptions.DieException;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.TileType;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;
import java.util.Random;
import java.util.function.UnaryOperator;

import static ru.itmo.roguelike.field.TileType.WATER;

public class Player extends Actor {
    private static final Random random = new Random();
    private IntCoordinate moveDirection = IntCoordinate.getZeroPosition();
    private IntCoordinate attackDirection = IntCoordinate.getZeroPosition();
    private boolean doAttack = false;

    private Player() {
        drawableDescriptor.setColor(Color.RED);
        init(100);
    }

    private static class PlayerHolder {
        public static final Player PLAYER_INSTANCE = new Player();
    }

    public static Player getPlayer() {
        return PlayerHolder.PLAYER_INSTANCE;
    }

    @Override
    public void collide(Collidable c) {

    }

    @Override
    public void act(Field field) {
        TileType currTile = field.getTileType(position.getX(), position.getY());

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
        moveDirection = IntCoordinate.getZeroPosition();
        attackDirection = IntCoordinate.getZeroPosition();
        doAttack = false;
    }

    private void fire(Field field) {
        Fireball fireball = new Fireball(new IntCoordinate(attackDirection.getX(), attackDirection.getY()));
        fireball.getPosition().setX(position.getX());
        fireball.getPosition().setY(position.getY());
        fireball.act(field);
    }

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

    public void move(IntCoordinate by) {
        this.moveDirection.add(by);
    }

    public void attack(IntCoordinate direction) {
        doAttack = true;
        attackDirection.add(direction);
    }

    public void setCoordinate(IntCoordinate position) {
        this.position = position;
    }

}
