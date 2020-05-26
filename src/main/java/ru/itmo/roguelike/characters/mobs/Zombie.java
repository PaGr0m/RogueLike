package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.Camera;

import java.awt.*;

public class Zombie extends Enemy {
    public Zombie() {
        super();
    }

    public Zombie(Actor target) {
        super(target);
    }

    {
        drawableDescriptor.setColor(Color.CYAN);
    }

}
