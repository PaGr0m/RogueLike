package ru.itmo.roguelike.characters.attack;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.projectiles.Sword;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.IntCoordinate;

public class SwordAttack extends Attack {
    public static final int COOLDOWN_TIME = 20;
    private Sword sword;
    private Player player;

    public SwordAttack(Actor actor) {
        super(COOLDOWN_TIME, actor);
        if (actor instanceof Player) {
            player = (Player) actor;
        }
    }

    /**
     * Moves sword root to actor center
     */
    @Override
    public void act() {
        if (sword != null) {
            IntCoordinate delta = new IntCoordinate(
                    (int) actor.getShape().getBounds().getCenterX(),
                    (int) actor.getShape().getBounds().getCenterY()
            );
            sword.setPosition(actor.getPosition());
            sword.getPosition().add(delta);
        }
        super.act();
    }

    /**
     * [Re]creates sword
     *
     * @param field -- game field
     */
    @Override
    public void runAttack(Field field) {
        if (sword != null) {
            sword.die();
        }
        if (player != null) {
            sword = new Sword(player);
        }
        sword = new Sword();
    }
}
