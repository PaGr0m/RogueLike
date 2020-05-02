package ru.itmo.roguelike.characters.mobs;

import java.awt.*;

public class Zombie extends Enemy{
    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0xFFFF00));
    }
}
