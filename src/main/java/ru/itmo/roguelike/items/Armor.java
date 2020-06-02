package ru.itmo.roguelike.items;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.particles.MovingUpText;

import java.awt.*;

public abstract class Armor extends Collectible {
    boolean onActor = false;
    /**
     * Resistance of additional armor in percent
     * Getting mob_damage*def*armorResistance points of damage
     */
    protected double armorResistance;

    @Override
    public void use(Actor actor) {
        new MovingUpText(actor.getPosition(), "Put on armor", Color.RED);
        actor.protect(this);
        this.onActor = true;
    }

    @Override
    public boolean isOnActor() {
        return onActor;
    }

    @Override
    public String getSort() {
        return null;
    }

    public double getArmorResistance() {
        return armorResistance;
    }
}
