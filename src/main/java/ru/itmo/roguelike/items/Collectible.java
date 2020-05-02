package ru.itmo.roguelike.items;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.render.drawable.Drawable;

import java.awt.*;

public abstract class Collectible extends Drawable implements Collidable {

    BonusType bonus;
    int bonusSize;
    protected int positionX;
    protected int positionY;
    private int width;
    private int height;
    private Color color;

    @Override
    public void collide(Collidable c) {
        c.collide(this);
        //нужно еще убрать объект с поля
    }
}