package ru.itmo.roguelike.characters.attack;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.Coordinate;


/**
 * Represents attack ability
 */
public abstract class Attack {
    protected Coordinate direction;
    protected int coolDown = 0;
    protected final Actor actor;

    private final int coolDownTime;

    /**
     * @param coolDownTime -- time interval between attacks (as number of `act()` calls)
     * @param actor -- attacker
     */
    public Attack(int coolDownTime, Actor actor) {
        this.coolDownTime = coolDownTime;
        this.actor = actor;
    }

    /**
     * @param direction -- attack direction
     */
    public void setDirection(Coordinate direction) {
        this.direction = direction;
    }

    public Coordinate getDirection() {
        return direction;
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
     * @param field -- start attack (do nothing if coolDown != 0)
     */
    public final void attack(Field field) {
        if (coolDown <= 0) {
            coolDown = coolDownTime;
            runAttack(field);
        }
    }


    /**
     * Unconditionally starts an attack
     * @param field -- game field
     */
    public abstract void runAttack(Field field);
}
