package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.Camera;

import java.awt.*;

/**
 * Моб-пузырь
 */
public class Slime extends Enemy {
    public Slime() {
        drawableDescriptor.setColor(new Color(0x5900FF));
    }

    public Slime(Actor target) {
        super(target);
    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        super.draw(graphics, camera);
    }

}
