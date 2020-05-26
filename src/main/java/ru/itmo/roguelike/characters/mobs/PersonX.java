package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.Camera;

import java.awt.*;

/**
 * Like a boss on level
 */
public class PersonX extends Enemy {
    public PersonX() {
    }

    public PersonX(Actor target) {
        super(target);
    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0xFF00FF));
        super.draw(graphics, camera);
    }

}
