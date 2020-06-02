package ru.itmo.roguelike.items;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.particles.MovingUpText;

import java.awt.*;

public class Armor extends Collectible {
    boolean onActor = false;

    @Override
    public void use(Actor actor) {
        new MovingUpText(actor.getPosition(), "Put on armor", Color.RED);
        actor.protect(bonusSize);
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
}
