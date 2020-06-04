package ru.itmo.roguelike.map;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.utils.Pair;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.util.Arrays;

public class Tile extends Drawable implements Collidable {
    public final static int WIDTH_IN_PIX = 10;
    public final static int HEIGHT_IN_PIX = 10;

    private int x = 0, y = 0;

    private TileType type = TileType.GRASS;

    public Tile(CollideManager collideManager) {
        collideManager.registerStatic(this);
    }

    public void reInit(float value) {
        Pair<TileType, Float> typeAndIntensity = TileType.getTypeAndIntensity(value);

        type = typeAndIntensity.getFirst();
        float intens = typeAndIntensity.getSecond();

        float[] color = {0, 0, 0};
        type.getMainColor().getColorComponents(ColorSpace.getInstance(ColorSpace.CS_sRGB), color);
        Color realColor = new Color(color[0] * intens, color[1] * intens, color[2] * intens);
        drawableDescriptor.setColor(realColor);
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
        drawableDescriptor.setX(getX()).setY(getY());
    }

    public boolean isSolid() {
        return type.isSolid();
    }

    @Override
    public void draw() {

    }

    @Override
    public int getX() {
        return x * WIDTH_IN_PIX;
    }

    @Override
    public int getY() {
        return y * HEIGHT_IN_PIX;
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
