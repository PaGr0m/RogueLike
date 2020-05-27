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

    protected void init(IntCoordinate position, int hp) {
        this.position.setXY(position);
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

    public void setPosition(IntCoordinate position) {
        this.position = new IntCoordinate(position);
    }

    @Override
    public IntCoordinate getLastPosition() {
        return new IntCoordinate(mover.getLastMove());
    }

    public void act(Field field) {
        if (field.getTileType(position.getX(), position.getY()) == TileType.BADROCK) {
            this.die();
        }
    }

    public void go(IntCoordinate by, Field field) {
        IntCoordinate newCoord = mover.move(position, by);
        TileType nextTile = field.getTileType(newCoord.getX(), newCoord.getY());
        if (!nextTile.isSolid()) {
            position.setXY(newCoord);
        }
    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        drawableDescriptor.setX(position.getX()).setY(position.getY());
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
