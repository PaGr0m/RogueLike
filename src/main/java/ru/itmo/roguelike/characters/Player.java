package ru.itmo.roguelike.characters;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.attack.Attack;
import ru.itmo.roguelike.characters.attack.FireballAttack;
import ru.itmo.roguelike.characters.movement.Mover;
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
    private final Attack attackMethod = new FireballAttack(this);

    private boolean doAttack = false;
    private int level;
    private float exp;

    public Player() {
        drawableDescriptor.setColor(Color.RED);
        init(100);
        resetExp();
    }

    @Override
    public void collide(Collidable c) {

    }

    @Override
    public void act(Field field) {
        TileType currTile = field.getTileType(position);

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
        moveDirection = IntCoordinate.getZeroPosition();
        attackMethod.setDirection(IntCoordinate.getZeroPosition());
        doAttack = false;
    }

    /**
     * Reset all exp and level for player
     */
    private void resetExp() {
        level = 1;
        exp = 0;
    }

    @Override
    public void die() {
        init(new IntCoordinate(
                        random.nextInt(1_000_000) - 500_000,
                        random.nextInt(1_000_000) - 500_000
                ),
                maxHp
        );
        resetExp();
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
        attackMethod.getDirection().add(direction);
    }

    public void setCoordinate(IntCoordinate position) {
        this.position = position;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Return maximum XP for current level
     * <p>
     * MaxXP = 9 + level^2
     *
     * @return max xp
     */
    public float getMaxExp() {
        return 9 + level * level;
    }

    public void addExp(float exp) {
        this.exp += exp;
        System.out.println("Get exp " + this.exp);
        if (this.exp >= getMaxExp()) {
            this.exp -= getMaxExp();
            ++level;
            System.out.println("New level " + level);
        }
    }
}
