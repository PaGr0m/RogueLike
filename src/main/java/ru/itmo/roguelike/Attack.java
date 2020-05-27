package ru.itmo.roguelike;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.Coordinate;

public abstract class Attack {
    protected Coordinate direction;
    protected int coolDown = 0;
    protected final Actor actor;

    private final int coolDownTime;

    public Attack(int coolDownTime, Actor actor) {
        this.coolDownTime = coolDownTime;
        this.actor = actor;
    }

    public void setDirection(Coordinate direction) {
        this.direction = direction;
    }

    public Coordinate getDirection() {
        return direction;
    }

    public void act() {
        if (coolDown > 0) {
            --coolDown;
        }
    }

    public void attack(Field field) {
        if (coolDown <= 0) {
            coolDown = coolDownTime;
            runAttack(field);
        }
    }

    public abstract void runAttack(Field field);
}
