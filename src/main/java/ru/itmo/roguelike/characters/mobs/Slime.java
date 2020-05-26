package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import java.awt.*;

/**
 * Моб-пузырь
 */
public class Slime extends Enemy {
    public Slime() { }

    public Slime(Actor target) {
        super(target);
    }

    {
        drawableDescriptor.setColor(new Color(0x5900FF));
    }

}
