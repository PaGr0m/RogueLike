package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import java.awt.*;

/**
 * Like a boss on level
 */
public class PersonX extends Enemy {
    public PersonX() {
    }

    public PersonX(Actor target) {
        super(target);
    }

    @Override
    public void draw() {
        drawableDescriptor.setX(coordinate.getX())
                          .setY(coordinate.getY())
                          .setColor(new Color(0xFF00FF));
    }
}
