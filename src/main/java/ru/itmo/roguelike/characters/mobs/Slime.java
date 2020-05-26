package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import java.awt.*;

/**
 * One of mob kind
 * Behaviour --- coward
 * Color --- purple-blue
 */
public class Slime extends Enemy {
    public Slime() {
    }

    public Slime(Actor target) {
        super(target);
    }

    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0x5900FF));
    }
}
