package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Player;

import java.awt.*;

public class Zombie extends Enemy{
    public Zombie(Player target) {
        super(target);
    }

    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0xFFFF00));
    }
}
