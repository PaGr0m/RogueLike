package ru.itmo.roguelike.characters.attack;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.inventory.Usable;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;


/**
 * Represents attack ability
 */
public abstract class Attack implements Usable {
    protected final Actor actor;
    private final int coolDownTime;
    protected IntCoordinate direction;
    protected int coolDown = 0;

    /**
     * @param coolDownTime time interval between attacks (as number of `act()` calls)
     * @param actor        attacker
     */
    public Attack(int coolDownTime, Actor actor) {
        this.coolDownTime = coolDownTime;
        this.actor = actor;
    }

    /**
     * In case of attack usage is: Actor should start using this as his currently selected attack
     */
    @Override
    public void use(@NotNull Actor actor) {
        actor.setAttackMethod(this);
    }

    /**
     * Attack may be used infinite amount of time by default
     *
     * @return Constantly false {@code System.out.println()}
     */
    @Override
    public boolean isUsed() {
        return false;
    }

    public IntCoordinate getDirection() {
        return direction;
    }

    /**
     * @param direction attack direction
     */
    public void setDirection(IntCoordinate direction) {
        this.direction = direction;
    }

    /**
     * process attack internal operations (decrease cooldown time etc)
     */
    public void act() {
        if (coolDown > 0) {
            --coolDown;
        }
    }

    /**
     * @param field start attack (do nothing if coolDown != 0)
     */
    public final void attack(Field field) {
        if (coolDown <= 0 && direction.lenL1() > 0) {
            coolDown = coolDownTime;
            runAttack(field);
        }
    }


    /**
     * Unconditionally starts an attack
     *
     * @param field game field
     */
    public abstract void runAttack(Field field);

}
