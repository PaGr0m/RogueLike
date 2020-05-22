package ru.itmo.roguelike.characters.projectiles;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.manager.actormanager.ProjectileManager;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;

import java.util.function.Consumer;

/**
 * Class of projectile objects
 */
public class Projectile extends Actor {

    public Projectile() {
        ProjectileManager.addToRegister(this);
        CollideManager.register(this);
    }

    @Override
    public void go() {
        super.go();
    }

    @Override
    public void die() {
        super.die();
        Projectile.unregister(this);
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
    public void draw() {

    }
}
