package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.strategy.MobBehavior;

import java.awt.*;

/**
 * Моб-пузырь
 */
public class Slime extends Enemy {
    public Slime(Actor target) {
        super(target);
    }

    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0x5900FF));
    }
}
