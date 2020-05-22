package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.mobs.strategy.*;
import ru.itmo.roguelike.manager.actormanager.MobManager;
import ru.itmo.roguelike.utils.Pair;

public abstract class Enemy extends Actor implements Collidable {
    private Actor target;
    private MobBehavior strategy;

    public Enemy(Actor target) {
        this(target, new PassiveBehavior());
    }

    public Enemy(Actor target, MobBehavior strategy) {
        this.target = target;
        this.strategy = strategy;
    }

    {
        MobManager.addToRegister(this);
    }

    public void setBehaviour(MobBehavior strategy) {
        this.strategy = strategy;
    }

    public void setTarget(Actor target) {
        this.target = target;

        if (this.strategy instanceof WithTarget) {
            ((WithTarget) this.strategy).setTarget(target);
        }
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
        Pair<Integer, Integer> path = strategy.getPath();

        positionX += path.getFirst() * 20;
        positionY += path.getSecond() * 20;
    }
}
