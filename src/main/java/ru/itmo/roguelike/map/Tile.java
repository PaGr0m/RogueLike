package ru.itmo.roguelike.map;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.render.drawable.Drawable;

import java.awt.*;

public class Tile extends Drawable implements Collidable {
    public final static int WIDTH_IN_PIX = 10;
    public final static int HEIGHT_IN_PIX = 10;

    private int x = 0, y = 0;

    private TileType type = TileType.GRASS;

    public Tile(CollideManager collideManager) {
        collideManager.registerStatic(this);
    }

    public void reInit(float value) {
        int col = (int) (value * 255.0f);
        if (col > 127) {
            col = (col - 128) * 2;
            type = TileType.ROCK;
            drawableDescriptor.setColor(new Color(col, col / 2, 0));
        } else {
            col *= 2;
            type = TileType.GRASS;
            drawableDescriptor.setColor(new Color(col / 2, col, 0));
        }
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
        drawableDescriptor.setX(getX()).setY(getY());
    }

    public boolean isSolid() {
        return type == TileType.ROCK;
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
