package ru.itmo.roguelike.characters.attack;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.field.Field;

public class FireballAttack extends Attack {
    public static final int COOLDOWN_TIME = 10;

    public FireballAttack(Actor actor) {
        super(COOLDOWN_TIME, actor);
    }

    /**
     * Throws {@link Fireball} in the direction of this.direction
     *
     * @param field -- game field
     */
    @Override
    public void runAttack(Field field) {
        Fireball fireball;
        fireball = new Fireball(direction, actor);
        fireball.setPosition(actor.getPosition());
        fireball.act(field);
    }
}
