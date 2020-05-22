package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;

import java.awt.*;

public class Zombie extends Enemy {
    public Zombie(Actor target) {
        super(target);
    }

    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0xFFFF00));
    }
}
