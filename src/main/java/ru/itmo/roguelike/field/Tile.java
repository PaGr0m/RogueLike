package ru.itmo.roguelike.field;

import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.utils.IntCoordinate;
import ru.itmo.roguelike.utils.Pair;

import java.awt.*;
import java.awt.color.ColorSpace;

/**
 * The minimal block of which the playing field consists.
 */
public class Tile extends Drawable {
    public final static int WIDTH_IN_PIX = 10;
    public final static int HEIGHT_IN_PIX = 10;

    private final IntCoordinate position = IntCoordinate.getZeroPosition();

    private TileType type = TileType.GRASS;

    /**
     * Creates an empty tile. Must be initialized after.
     */
    public Tile() {
        super(true);
    }

    /**
     * Creates empty tile and initialized it.
     * @param value real number in range [0, 1]. Needed to compute {@see TileType}.
     */
    public Tile(float value) {
        super(true);
        reInit(value);
    }

    /**
     * Reinitializes tile with a new value. Tile type may change.
     * @param value real number in range [0, 1]. Needed to compute {@see TileType}.
     */
    public void reInit(float value) {
        Pair<TileType, Float> typeAndIntensity = TileType.getTypeAndIntensity(value);

        type = typeAndIntensity.getFirst();
        float intens = typeAndIntensity.getSecond();

        if (type == TileType.BADROCK) return;

        float[] color = {0, 0, 0};
        type.getMainColor().getColorComponents(ColorSpace.getInstance(ColorSpace.CS_sRGB), color);
        Color realColor = new Color(color[0] * intens, color[1] * intens, color[2] * intens);
        drawableDescriptor.setColor(realColor);
    }

    public TileType getType() {
        return type;
    }

    /**
     * @param x X coordinate in number of tiles
     * @param y Y coordinate in number of tiles
     */
    public void setXY(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
        drawableDescriptor.setPosition(getPos());
    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        if (type != TileType.BADROCK) {
            super.draw(graphics, camera);
        }
    }

    /**
     * @return X coordinate in world coordinates
     */
    public int getX() {
        return position.getX() * WIDTH_IN_PIX;
    }

    /**
     * @return Y coordinate in world coordinates
     */
    public int getY() {
        return position.getY() * HEIGHT_IN_PIX;
    }

    /**
     * @return Tile position in world coordinate
     */
    public IntCoordinate getPos() {
        IntCoordinate res = new IntCoordinate(position);
        res.mult(WIDTH_IN_PIX, HEIGHT_IN_PIX);
        return res;
    }

}
