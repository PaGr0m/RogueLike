package ru.itmo.roguelike.characters.attack;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.field.Field;

public class FireballAttack extends Attack {
    public static final int COOLDOWN_TIME = 10;
    private Player player;

    public FireballAttack(Actor actor) {
        super(COOLDOWN_TIME, actor);
        if (actor instanceof Player) {
            player = (Player) actor;
        }
    }

    /**
     * Throws {@link Fireball} in the direction of this.direction
     *
     * @param field -- game field
     */
    @Override
    public void runAttack(Field field) {
        if (player != null) {
            Fireball fireball = new Fireball(direction, player);
        }
        Fireball fireball = new Fireball(direction);
        fireball.setPosition(actor.getPosition());
        fireball.act(field);
    }
}
