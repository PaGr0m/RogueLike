package ru.itmo.roguelike.field;

import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.characters.mobs.Zombie;
import ru.itmo.roguelike.characters.mobs.strategy.AggressiveBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobWithTarget;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.utils.Pair;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.util.Random;

public class Tile extends Drawable {
    public final static int WIDTH_IN_PIX = 10;
    public final static int HEIGHT_IN_PIX = 10;

    private int x = 0, y = 0;

    private TileType type = TileType.GRASS;

    public Tile() {
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

    public TileType getType() {
        return type;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
        drawableDescriptor.setX(getX()).setY(getY());
    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        super.draw(graphics, camera);
    }

    public int getX() {
        return x * WIDTH_IN_PIX;
    }

    public int getY() {
        return y * HEIGHT_IN_PIX;
    }

    public int getWidth() {
        return WIDTH_IN_PIX;
    }

    public int getHeight() {
        return HEIGHT_IN_PIX;
    }

}
