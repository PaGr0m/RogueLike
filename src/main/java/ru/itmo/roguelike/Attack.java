package ru.itmo.roguelike;

import ru.itmo.roguelike.utils.Coordinate;

public abstract class Attack {
    private Coordinate direction;
    private int coolDown;

    private final int coolDownTime;

    public Attack(int coolDownTime) {
        this.coolDownTime = coolDownTime;
    }

    public void setDirection(Coordinate direction) {
        this.direction = direction;
    }

    public void act() {
        if (coolDown > 0) {
            --coolDown;
        }
    }

    public void attack() {
        coolDown = coolDownTime;
    }
}
