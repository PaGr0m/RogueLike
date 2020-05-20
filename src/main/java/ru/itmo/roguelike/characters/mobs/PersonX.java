package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.strategy.MobBehavior;

import java.awt.*;

/**
 * Like a boss on level
 */
public class PersonX extends Enemy {
    public PersonX(Player target) {
        super(target);
    }

    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0xFF00FF));
    }
}
