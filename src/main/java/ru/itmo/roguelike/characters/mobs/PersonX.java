package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import java.awt.*;

/**
 * One of mob kind
 * Behaviour --- agressive
 * Color --- fuchsia
 */
public class PersonX extends Enemy {
    public PersonX() {
    }

    public PersonX(Actor target) {
        super(target);
    }

    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0xFF00FF));
    }
}
