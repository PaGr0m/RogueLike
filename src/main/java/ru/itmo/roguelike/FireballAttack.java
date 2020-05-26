package ru.itmo.roguelike;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.Pair;

public class FireballAttack extends Attack {
    public static final int CD_TYPE = 10;
    public final Actor actor;

    public FireballAttack(Actor actor) {
        super(CD_TYPE);
        this.actor = actor;
    }

    @Override
    public void runAttack(Field field) {
        Fireball fireball = new Fireball(new Pair<>(getDirection().getX(), getDirection().getY()));
        fireball.setY(actor.getX());
        fireball.setX(actor.getY());
        fireball.act(field);
    }
}
