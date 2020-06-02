package ru.itmo.roguelike.characters;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.attack.FireballAttack;
import ru.itmo.roguelike.characters.attack.SwordAttack;
import ru.itmo.roguelike.characters.inventory.Inventory;
import ru.itmo.roguelike.characters.inventory.Usable;
import ru.itmo.roguelike.characters.movement.Mover;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.TileType;
import ru.itmo.roguelike.items.Collectible;
import ru.itmo.roguelike.manager.eventmanager.Event;
import ru.itmo.roguelike.manager.eventmanager.EventManager;
import ru.itmo.roguelike.manager.gamemanager.GameManager;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.utils.IntCoordinate;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import static ru.itmo.roguelike.field.TileType.BADROCK;
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
    private Instant lastInventoryWarning = Instant.now();
    private EventManager eventManager;

    @Inject
    public Player(EventManager eventManager) {
        this.eventManager = eventManager;

        drawableDescriptor.setColor(Color.RED);
        init(100);

        resetExp();
        resetInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void collide(Collidable c) {
        if (c instanceof Collectible) {
            Collectible collectible = (Collectible) c;

            if (!inventory.isFull()) {
                collectible.pickUp();
                inventory.setNextFreeItem(collectible);
            } else {
                position.set(mover.getLastMove());

                if (Duration.between(lastInventoryWarning, Instant.now()).getSeconds() > 1) {
                    new MovingUpText(position, "Inventory is full", Color.RED);
                    lastInventoryWarning = Instant.now();
                }
            }
        }
    }

    @Override
    public void act(Field field) {
        TileType currTile = field.getTileType(position);

        if (currTile == WATER) {
            moveDirection.div(2);
        } else {
            position.div(10); // Adjusting the position of the player after walking on the water
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
                inventory.removeItem(i);
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

    public void reborn() {
        init(maxHp);
    }

    /**
     * Clears contents of player's inventory. Useful for handling death of player.
     */
    private void resetInventory() {
        inventory.clear();

        //FIXME: for testing purposes
        inventory.setItem(new FireballAttack(this), 0);
        inventory.setItem(new SwordAttack(this), 1);
    }

    @Override
    public void die() {
        mover = new Mover();
        resetExp();
    }

    public void activateMoveEffect(Class<? extends Mover> effect, Event event) {
        if (mover.contains(effect)) {
            return;
        }

        long startTime = GameManager.GLOBAL_TIME;

        try {
            mover = effect.getConstructor(Mover.class).newInstance(mover);
            eventManager.addDrawableEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }

        eventManager.add(() -> {
            event.setCurr((int) (GameManager.GLOBAL_TIME - startTime));
            event.run();
            if (event.getCurr() > event.getDuration()) {
                Player.this.deactivateMoveEffect(effect);
                eventManager.removeDrawableEvent(event);
                return false;
            }
            return true;
        });
    }

    public void deactivateMoveEffect(Class<? extends Mover> effect) {
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

    public void saveToFile(DataOutputStream output) throws IOException {
        output.writeInt(position.getX());
        output.writeInt(position.getY());
        output.writeInt(level);
        output.writeFloat(exp);

    }

    public void loadFromFile(DataInputStream inputStream) throws IOException {
        position.setX(inputStream.readInt());
        position.setY(inputStream.readInt());
        level = inputStream.readInt();
        exp = inputStream.readFloat();
    }

    public void fixPosition(Field field) {
        final IntCoordinate moveUp = new IntCoordinate(0, -10);

        TileType type = field.getTileType(position);
        while (type.isSolid()) {
            if (type == BADROCK) {
                field.reInit(position);
            }
            type = field.getTileType(position);
            position.add(moveUp);
        }
    }

    public int getMaxHP() {
        return maxHp;
    }
}
