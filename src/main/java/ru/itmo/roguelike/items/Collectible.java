package ru.itmo.roguelike.items;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.render.drawable.Drawable;

import java.awt.*;

public abstract class Collectible extends Drawable implements Collidable {

    protected int positionX;
    protected int positionY;
    protected int width;
    protected int height;
    protected Color color;
    BonusType bonus;
    int bonusSize;

    @Override
    public void collide(Collidable c) {
        c.collide(this);
        //нужно еще убрать объект с поля
    }

    @Override
    public int getX() {
        return positionX;
    }

    @Override
    public int getY() {
        return positionY;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}