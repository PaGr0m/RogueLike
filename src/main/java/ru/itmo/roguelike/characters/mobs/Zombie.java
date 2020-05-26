package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import java.awt.*;

public class Zombie extends Enemy {
    {
        drawableDescriptor.setColor(Color.CYAN);
    }

    public Zombie() {
        super();
    }

    public Zombie(Actor target) {
        super(target);
    }

}
