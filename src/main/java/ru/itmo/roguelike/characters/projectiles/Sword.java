package ru.itmo.roguelike.characters.projectiles;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.field.Field;

import java.awt.*;
import java.awt.geom.Line2D;

public class Sword extends Projectile {
    private final Actor actor;
    private int ttl = 10;

    {
        damage = 10;
    }

    public Sword(Actor actor) {
        super(new Drawer() {
            double angle = 0;
            @Override
            public void draw(Graphics2D graphics, int x, int y) {
                Stroke old = graphics.getStroke();
                graphics.setStroke(new BasicStroke(10));
                graphics.drawLine(x + 5, y + 5, x + 5 + (int) (20 * Math.cos(angle)),
                        y + 5 + (int) (20 * Math.sin(angle)));
                angle += 0.3;
                graphics.setStroke(old);
            }
        });
        drawableDescriptor.setColor(Color.BLACK);
        this.actor = actor;
    }

    @Override
    public void act(Field field) {
        ttl--;
        if (ttl == 0) {
            die();
        }
    }

    @Override
    public void collide(Collidable c) {
        if (c instanceof Enemy) {
            ((Enemy) c).strike(this.damage);
        }
    }

}
