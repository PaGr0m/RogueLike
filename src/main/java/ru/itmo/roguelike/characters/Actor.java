package ru.itmo.roguelike.characters;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.TileType;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.utils.Pair;

import java.awt.*;

public abstract class Actor extends Drawable implements Collidable {
    protected int positionX;
    protected int positionY;
    protected Pair<Integer, Integer> direction;
    protected int damage;
    protected int hp;
    protected float radius;

    {
        CollideManager.register(this);
    }

    public Actor() {
    }

    public Actor(Drawer drawer) {
        super(drawer);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
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

    public void go(Field field) {
        if (field.getTileType(positionX, positionY) == TileType.BADROCK) {
            this.die();
        }
    }

    public void goTo(int toX, int toY, Field field) {
        TileType nextTile = field.getTileType(toX, toY);

        if (!nextTile.isSolid()) {
            positionX = toX;
            positionY = toY;
        }
    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        drawableDescriptor.setX(positionX).setY(positionY);
        super.draw(graphics, camera);
    }

    public void die() {
        Drawable.unregister(this);
        CollideManager.unregister(this);
    }

    public void strike(int damage) {
        this.hp -= damage;
        if (hp < 0) die();
    }
}
