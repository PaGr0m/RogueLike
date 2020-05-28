package ru.itmo.roguelike.characters;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.movement.Mover;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.TileType;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;

public abstract class Actor extends Drawable implements Collidable {
    protected IntCoordinate position = IntCoordinate.getZeroPosition();
    protected IntCoordinate direction;
    protected int damage;

    protected int maxHp;
    protected int hp;

    protected float radius;
    protected Mover mover = new Mover();

    {
        CollideManager.register(this);
    }

    public Actor() {
    }

    public Actor(Drawer drawer) {
        super(drawer);
    }

    protected void init(int positionX, int positionY, int hp) {
        this.position.setX(positionX);
        this.position.setY(positionY);

        init(hp);
    }

    protected void init(int hp) {
        maxHp = hp;
        this.hp = maxHp;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public IntCoordinate getPosition() {
        return position;
    }

    @Override
    public IntCoordinate getLastPosition() {
        return mover.getLastPosition();
    }

    @Override
    public int getWidth() {
        return 10; // FIXME: magic number
    }

    @Override
    public int getHeight() {
        return 10; // FIXME: magic number
    }

    public int getHp() {
        return hp;
    }

    public void act(Field field) {
        if (field.getTileType(position.getX(), position.getY()) == TileType.BADROCK) {
            this.die();
        }
    }

    public void go(IntCoordinate by, Field field) {
        int newX = mover.moveX(position.getX(), by.getX());
        int newY = mover.moveY(position.getY(), by.getY());

        TileType nextTile = field.getTileType(newX, newY);

        if (!nextTile.isSolid()) {
            position.setX(newX);
            position.setY(newY);
        }
    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        drawableDescriptor.setPosition(position);
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
