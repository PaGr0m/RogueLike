package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.manager.actormanager.MobManager;

public abstract class Enemy extends Actor implements Collidable {
    Actor target = null;

    @Override
    public void collide(Collidable c) {
        // если настигли цель
        if (c.equals(target)) {
            target.getDamage(this.damage);
        }
    }

    @Override
    public void die() {
        MobManager.deleteFromRegister(this);
    }
}
