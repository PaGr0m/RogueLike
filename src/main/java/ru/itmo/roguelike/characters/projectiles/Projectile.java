package ru.itmo.roguelike.characters.projectiles;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.manager.actormanager.ProjectileManager;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.drawable.Drawable;

import java.awt.*;

/**
 * Class of projectile objects
 */
public class Projectile extends Actor {

    public Projectile() { }

    public Projectile(Drawer drawer) {
        super(drawer);
    }

    {
        ProjectileManager.addToRegister(this);
        CollideManager.register(this);
    }

    @Override
    public void go(Field field) {
        if (field.getTileType(positionX, positionY).isSolid()) {
            die();
        }
    }

    @Override
    public void die() {
        super.die();
        Drawable.unregister(this);
        ProjectileManager.deleteFromRegister(this);
        CollideManager.unregister(this);
    }

    /**
     * Method that specify what exactly do object when collide other
     *
     * @param c --- object with which this collided
     */
    @Override
    public void collide(Collidable c) {

    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        super.draw(graphics, camera);
    }
}
