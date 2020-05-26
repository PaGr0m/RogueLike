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

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        drawableDescriptor.setX(this.positionX).setY(this.positionY).setColor(new Color(0xFFFF00));
        super.draw(graphics, camera);
    }

}
