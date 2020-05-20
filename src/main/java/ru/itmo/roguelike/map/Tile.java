package ru.itmo.roguelike.map;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.render.drawable.Drawable;

import java.awt.*;

public class Tile extends Drawable implements Collidable {
    public final static int WIDTH_IN_PIX = 10;
    public final static int HEIGHT_IN_PIX = 10;

    private int x = 0, y = 0;

    public Tile() { }

    public void reInit(float value) {
        int col = (int) (value * 255.0f);
        if (col > 127) {
            col = (col - 128) * 2;
            drawableDescriptor.setColor(new Color(col, col / 2, 0));
        } else {
            col *= 2;
            drawableDescriptor.setColor(new Color(col / 2, col, 0));
        }
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
        drawableDescriptor.setX(x * WIDTH_IN_PIX).setY(y * HEIGHT_IN_PIX);
    }


    @Override
    public void draw() {

    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
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
        c.collide(this);
    }
}
