package ru.itmo.roguelike.field;

import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.utils.IntCoordinate;
import ru.itmo.roguelike.utils.Pair;

import java.awt.*;
import java.awt.color.ColorSpace;

public class Tile extends Drawable {
    public final static int WIDTH_IN_PIX = 10;
    public final static int HEIGHT_IN_PIX = 10;

    private final IntCoordinate position = IntCoordinate.getZeroPosition();

    private TileType type = TileType.GRASS;

    public Tile() {
        super(true);
    }

    public Tile(float value) {
        super(true);
        reInit(value);
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
        this.position.setX(x);
        this.position.setY(y);
        drawableDescriptor.setPosition(getPos());
    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        super.draw(graphics, camera);
    }

    public int getX() {
        return position.getX() * WIDTH_IN_PIX;
    }

    public int getY() {
        return position.getY() * HEIGHT_IN_PIX;
    }

    public IntCoordinate getPos() {
        IntCoordinate res = new IntCoordinate(position);
        res.mult(WIDTH_IN_PIX, HEIGHT_IN_PIX);
        return res;
    }

    public int getWidth() {
        return WIDTH_IN_PIX;
    }

    public int getHeight() {
        return HEIGHT_IN_PIX;
    }

}
