package ru.itmo.roguelike.characters;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.utils.Pair;

import java.util.function.Consumer;

public abstract class Actor extends Drawable implements Collidable {
    protected int positionX;
    protected int positionY;
    protected Pair<Integer, Integer> direction;
    protected int damage;
    protected int hp;
    protected float radius;

    public Actor() {
        CollideManager.register(this);
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public int getX() {
        return positionX;
    }

    public void setX(int positionX) {
        this.positionX = positionX;
    }

    @Override
    public int getY() {
        return positionY;
    }

    public void setY(int positionY) {
        this.positionY = positionY;
    }

    @Override
    public int getWidth() {
        return 10; // FIXME: magic number
    }

    @Override
    public int getHeight() {
        return 10; // FIXME: magic number
    }

    public void go() {
        // todo call die if object is out of screen
    };

    public void die() {
        Drawable.unregister(this);
        CollideManager.unregister(this);
    }

    public void strike(int damage) {
        this.hp -= damage;
        if (hp < 0) die();
    }
}
