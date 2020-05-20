package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.manager.actormanager.MobManager;

import static java.lang.Integer.signum;

public abstract class Enemy extends Actor implements Collidable {
    Actor target;

    public Enemy(Player target) {
        this.target = target;
    }

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

    @Override
    public void go() {
        int stepX = signum(this.positionX - target.getX());
    }
}
