package ru.itmo.roguelike.characters.attack;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.projectiles.Sword;
import ru.itmo.roguelike.field.Field;

public class SwordAttack extends Attack {
    public static final int COOLDOWN_TIME = 20;
    private Sword sword;

    public SwordAttack(Actor actor) {
        super(COOLDOWN_TIME, actor);
    }

    /**
     * Moves sword root to actor center
     */
    @Override
    public void act() {
        if (sword != null) {
            sword.setX(actor.getX() + (int) actor.getShape().getBounds().getCenterX());
            sword.setY(actor.getY() + (int) actor.getShape().getBounds().getCenterY());
        }
        super.act();
    }

    /**
     * [Re]creates sword
     * @param field -- game field
     */
    @Override
    public void runAttack(Field field) {
        if (sword != null) {
            sword.die();
        }
        sword = new Sword();
    }
}
