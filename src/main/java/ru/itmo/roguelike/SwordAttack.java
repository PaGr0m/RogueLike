package ru.itmo.roguelike;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.projectiles.Sword;
import ru.itmo.roguelike.field.Field;

public class SwordAttack extends Attack {
    public static final int CD_TYPE = 20;
    private Sword sword;

    public SwordAttack(Actor actor) {
        super(CD_TYPE, actor);
    }

    @Override
    public void act() {
        if (sword != null) {
            sword.setX(actor.getX() + (int) actor.getShape().getBounds().getCenterX());
            sword.setY(actor.getY() + (int) actor.getShape().getBounds().getCenterY());
        }
        super.act();
    }

    @Override
    public void runAttack(Field field) {
        if (sword != null) {
            sword.die();
        }
        sword = new Sword();
    }
}
