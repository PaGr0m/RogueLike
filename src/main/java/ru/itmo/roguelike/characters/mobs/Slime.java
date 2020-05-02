package ru.itmo.roguelike.characters.mobs;

import java.awt.*;

/**
 * Моб-пузырь
 */
public class Slime extends Enemy {
    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0xFFF000));
    }
}
