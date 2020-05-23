package ru.itmo.roguelike.map;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.utils.Coordinate;

import java.awt.*;
import java.awt.color.ColorSpace;

public class Tile extends Drawable implements Collidable {
    public final static int WIDTH_IN_PIX = 10;
    public final static int HEIGHT_IN_PIX = 10;

    private Coordinate coordinate = new Coordinate(0, 0);
    private TileType type = TileType.GRASS;

    public Tile(CollideManager collideManager) {
        collideManager.registerStatic(this);
    }

    public void reInit(float value) {
        if (value > 0.5) {
            type = TileType.ROCK;
            value = (value - 0.5f) * 2;
        } else {
            value *= 2;
            type = TileType.GRASS;
        }
        float[] color = {0, 0, 0};
        type.getMainColor().getColorComponents(ColorSpace.getInstance(ColorSpace.CS_sRGB), color);
        Color realColor = new Color(color[0] * value, color[1] * value, color[2] * value);
        drawableDescriptor.setColor(realColor);
    }

    // TODO: доправить
    public void setXY(int x, int y) {
        coordinate.setX(x);
        coordinate.setY(y);
        drawableDescriptor.setX(getCoordinate().getX())
                          .setY(getCoordinate().getY());
    }

    public boolean isSolid() {
        return type.isSolid();
    }

    @Override
    public void draw() {

    }

    @Override
    public Coordinate getCoordinate() {
        return new Coordinate(coordinate.getX() * WIDTH_IN_PIX,
                              coordinate.getY() * HEIGHT_IN_PIX);
    }

    @Override
    public int getWidth() {
        return WIDTH_IN_PIX;
    }

    @Override
    public int getHeight() {
        return HEIGHT_IN_PIX;
    }

    @Override
    public void collide(Collidable c) {
//        c.collide(this);
    }
}
