package ru.itmo.roguelike.characters;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.items.Collectible;
import ru.itmo.roguelike.render.drawable.Drawable;

public abstract class Actor extends Drawable implements Collidable {
    protected int positionX;
    protected int positionY;
    protected int damage;
    protected int hp;

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

    public abstract void go();

    public abstract void die();

    public void getDamage(int damage) {
        this.hp -= damage;
    }
}
