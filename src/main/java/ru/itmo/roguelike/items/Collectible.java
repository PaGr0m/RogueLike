package ru.itmo.roguelike.items;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.Drawable;

public abstract class Collectible implements Drawable, Collidable {

    BonusType bonus;
    int bonusSize;

    @Override
    public void collide(Collidable c) {
        c.collide(this);
        //нужно еще убрать объект с поля
    }
}