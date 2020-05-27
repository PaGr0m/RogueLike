package ru.itmo.roguelike.characters;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.Attack;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.FireballAttack;
import ru.itmo.roguelike.SwordAttack;
import ru.itmo.roguelike.characters.movement.Mover;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.TileType;
import ru.itmo.roguelike.utils.Coordinate;
import ru.itmo.roguelike.utils.Pair;

import java.awt.*;
import java.util.function.UnaryOperator;

import static ru.itmo.roguelike.field.TileType.WATER;

public class Player extends Actor {
    private Coordinate moveDirection = Coordinate.zero();
    private Attack attackMethod = new FireballAttack(this);
    private boolean doAttack = false;

    public Player() {
        drawableDescriptor.setColor(Color.RED);
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
        if (doAttack) {
            attackMethod.attack(field);
        }

        attackMethod.act();

        super.act(field);
        resetState();
    }

    private void resetState() {
        moveDirection = Coordinate.zero();
        attackMethod.setDirection(Coordinate.zero());
        doAttack = false;
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

    public void move(Coordinate by) {
        this.moveDirection.add(by);
    }

    public void attack(Coordinate direction) {
        doAttack = true;
        attackMethod.getDirection().add(direction);
    }

}
