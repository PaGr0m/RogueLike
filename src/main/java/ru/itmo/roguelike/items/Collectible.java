package ru.itmo.roguelike.items;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;

public abstract class Collectible extends Drawable implements Collidable {

    private IntCoordinate position;
    protected int width;
    protected int height;
    protected Color color;
    protected BonusType bonus;
    protected int bonusSize;

    @Override
    public void collide(Collidable c) {
        c.collide(this);
        //нужно еще убрать объект с поля
    }

    @Override
    public IntCoordinate getPosition() {
        return position;
    }

}