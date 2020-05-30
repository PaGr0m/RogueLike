package ru.itmo.roguelike.field;

import ru.itmo.roguelike.utils.IntCoordinate;

/**
 * A chunk of tiles. Uses to optimize map generation process.
 * <b>SINGLE THREAD ONLY!</b>
 */
public class Chunk {
    public static final int WIDTH_IN_TILES = 16;
    public static final int HEIGHT_IN_TILES = 16;

    public static final int WIDTH_IN_PIX = WIDTH_IN_TILES * Tile.WIDTH_IN_PIX;
    public static final int HEIGHT_IN_PIX = WIDTH_IN_TILES * Tile.HEIGHT_IN_PIX;

    private static final float[][] chunkValues = new float[WIDTH_IN_TILES][HEIGHT_IN_TILES];
    // "Single thread only" cause

    private final Tile[][] tiles;
    private final MobPositionGenerator mobGenerator;
    private IntCoordinate position;

    /**
     * @param x chunk row number (in chunks grid)
     * @param y chunk column number (in chunks grid)
     * @param generator produces values for Tile creation
     * @param mobGenerator creates mobs on newly generated tiles
     */
    public Chunk(int x, int y, NoiseGenerator generator, MobPositionGenerator mobGenerator) {
        this.mobGenerator = mobGenerator;

        tiles = new Tile[chunkValues.length][chunkValues[0].length];
        for (int i = 0; i < chunkValues.length; i++) {
            for (int j = 0; j < chunkValues[0].length; j++) {
                tiles[i][j] = new Tile();
            }
        }

        reInitTiles(x, y, generator);
    }

    /**
     * @return tile at world-coordinate (x, y)
     */
    public Tile getTile(int x, int y) {
        int tileX = Math.floorDiv(x, Tile.WIDTH_IN_PIX);
        int tileY = Math.floorDiv(y, Tile.HEIGHT_IN_PIX);
        return tiles[tileY][tileX];
    }

    /**
     * Reinitializes tiles in chunk. Chunk state after the call is same as after the {@code new Chunk(x, y, generator, this.mobGenerator)} call.
     */
    public void reInitTiles(int x, int y, NoiseGenerator generator) {
        generator.generate(y, x, chunkValues);
        this.position = new IntCoordinate(x, y);
        for (int i = 0; i < chunkValues.length; i++) {
            for (int j = 0; j < chunkValues[0].length; j++) {
                tiles[i][j].setXY(x * WIDTH_IN_TILES + j,
                        y * HEIGHT_IN_TILES + i);
                tiles[i][j].reInit(chunkValues[i][j]);
                mobGenerator.addNewPosition(tiles[i][j]);
            }
        }
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }
}