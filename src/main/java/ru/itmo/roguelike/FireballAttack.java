package ru.itmo.roguelike;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.characters.projectiles.Sword;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.Coordinate;
import ru.itmo.roguelike.utils.Pair;

public class FireballAttack extends Attack {
    public static final int CD_TYPE = 10;

    public FireballAttack(Actor actor) {
        super(CD_TYPE, actor);
    }

    @Override
    public void runAttack(Field field) {
        Fireball fireball = new Fireball(new Coordinate(getDirection().getX(), getDirection().getY()));
        fireball.setX(actor.getX());
        fireball.setY(actor.getY());
        fireball.act(field);
    }
}
