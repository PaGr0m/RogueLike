package ru.itmo.roguelike.characters.mobs;

import java.awt.*;

/**
 * Like a boss on level
 */
public class PersonX extends Enemy {
    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0xFF00FF));
    }
}
