package ru.itmo.roguelike.characters.attack;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.Coordinate;

public class FireballAttack extends Attack {
    public static final int COOLDOWN_TIME = 10;

    public FireballAttack(Actor actor) {
        super(COOLDOWN_TIME, actor);
    }

    /**
     * Throws {@link Fireball} in the direction of this.direction
     * @param field -- game field
     */
    @Override
    public void runAttack(Field field) {
        Fireball fireball = new Fireball(new Coordinate(getDirection().getX(), getDirection().getY()));
        fireball.setX(actor.getX());
        fireball.setY(actor.getY());
        fireball.act(field);
    }
}
