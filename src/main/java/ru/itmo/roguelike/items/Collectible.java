package ru.itmo.roguelike.items;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.utils.Coordinate;

import java.awt.*;

public abstract class Collectible extends Drawable implements Collidable {

//    BonusType bonus;
    int bonusSize;
    protected Coordinate coordinate;
    protected int width;
    protected int height;
    protected Color color;

    @Override
    public void collide(Collidable c) {
        c.collide(this);
        //нужно еще убрать объект с поля
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
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