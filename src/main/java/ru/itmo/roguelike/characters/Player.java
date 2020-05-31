package ru.itmo.roguelike.characters;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.attack.FireballAttack;
import ru.itmo.roguelike.characters.attack.SwordAttack;
import ru.itmo.roguelike.characters.inventory.Inventory;
import ru.itmo.roguelike.characters.inventory.Usable;
import ru.itmo.roguelike.characters.movement.Mover;
import ru.itmo.roguelike.exceptions.DieException;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.TileType;
import ru.itmo.roguelike.items.Collectible;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.utils.IntCoordinate;

import javax.inject.Singleton;
import java.awt.*;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.UnaryOperator;

import static ru.itmo.roguelike.field.TileType.WATER;

@Singleton
public class Player extends Actor {
    private static final int INVENTORY_SIZE = 8;

    private static final Random random = new Random();
    private final Inventory inventory = new Inventory(INVENTORY_SIZE);
    private IntCoordinate moveDirection = IntCoordinate.getZeroPosition();
    private boolean doAttack = false;
    private int level;
    private float exp;

    public Player() {
        drawableDescriptor.setColor(Color.RED);
        init(100);
        resetExp();

        //FIXME: for testing purposes
        inventory.setItem(new FireballAttack(this), 1);
        inventory.setItem(new SwordAttack(this), 2);
    }

    @Override
    public void collide(Collidable c) {
        if (c instanceof Collectible) {
            Collectible collectible = (Collectible) c;
            collectible.pickUp();

            if (!inventory.isFull()) {
                OptionalInt i = inventory.setNextFreeItem(collectible);
            }
        }
    }

    @Override
    public void act(Field field) {
        TileType currTile = field.getTileType(position);

        if (currTile == WATER) {
            moveDirection.div(2);
        } else {
            position.div(10);
            position.mult(10);
        }

        go(moveDirection, field);
        if (doAttack) {
            attackMethod.attack(field);
        }

        attackMethod.act();

        super.act(field);
        resetState();
    }

    /**
     * Use item at specific position in inventory. If there is nothing in inventory at this position, does nothing.
     *
     * @param i number of inventory slot
     */
    public void useFromInventory(int i) {
        final Optional<Usable> item = inventory.getItem(i);
        item.ifPresent(usable -> {
            usable.use(this);
            if (usable.isUsed()) {
                inventory.setItem(null, i);
            }
        });
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

    public float getExp() {
        return exp;
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

    /**
     * Adds additional XP to player XP. Creates {@link MovingUpText} when leveling up.
     *
     * @param exp additional XP
     */
    public void addExp(float exp) {
        this.exp += exp;
        if (this.exp >= getMaxExp()) {
            this.exp -= getMaxExp();
            new MovingUpText(position, "LVL +1!", Color.YELLOW);
            ++level;
        }
    }
}
