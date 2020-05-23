package ru.itmo.roguelike.characters;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.utils.Coordinate;
import ru.itmo.roguelike.utils.Pair;

public abstract class Actor extends Drawable implements Collidable {
    protected Coordinate direction;
    protected Coordinate coordinate = new Coordinate(0, 0);
    //    protected int positionX;
//    protected int positionY;
    protected int damage;
    protected int hp;
    protected float radius;

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

//    @Override
//    public int getX() {
//        return positionX;
//    }
//
//    public void setX(int positionX) {
//        this.positionX = positionX;
//    }
//
//    @Override
//    public int getY() {
//        return positionY;
//    }
//
//    public void setY(int positionY) {
//        this.positionY = positionY;
//    }

    @Override
    public int getWidth() {
        return 10; // FIXME: magic number
    }

    @Override
    public int getHeight() {
        return 10; // FIXME: magic number
    }

    public abstract void go();

    public void die() {
        Drawable.unregister(this);
    }

    public void strike(int damage) {
        this.hp -= damage;
    }
}
