package ru.itmo.roguelike.characters;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.render.drawable.Drawable;

public abstract class Actor extends Drawable implements Collidable {
    protected int positionX;
    protected int positionY;
    protected int damage;
    protected int hp;

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public abstract void go();

    public abstract void die();

    public void getDamage(int damage) {
        this.hp -= damage;
    }
}
