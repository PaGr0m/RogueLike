package ru.itmo.roguelike.field;

public class Chunk {
    public static final int WIDTH_IN_TILES = 16;
    public static final int HEIGHT_IN_TILES = 16;

    public static final int WIDTH_IN_PIX = WIDTH_IN_TILES * Tile.WIDTH_IN_PIX;
    public static final int HEIGHT_IN_PIX = WIDTH_IN_TILES * Tile.HEIGHT_IN_PIX;

    private static final float[][] chunkValues = new float[WIDTH_IN_TILES][HEIGHT_IN_TILES];
    // Single thread only!

    public final Tile[][] tiles;
    private int x, y;

    private final MobPositionGenerator mobGenerator;

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

    public TileType getTileType(int x, int y) {
        int tileX = Math.floorDiv(x, Tile.WIDTH_IN_PIX);
        int tileY = Math.floorDiv(y, Tile.HEIGHT_IN_PIX);
        return tiles[tileY][tileX].getType();
    }

    public void reInitTiles(int x, int y, NoiseGenerator generator) {
        generator.generate(y, x, chunkValues);
        this.x = x;
        this.y = y;
        for (int i = 0; i < chunkValues.length; i++) {
            for (int j = 0; j < chunkValues[0].length; j++) {
                tiles[i][j].setXY(x * WIDTH_IN_TILES + j,
                        y * HEIGHT_IN_TILES + i);
                tiles[i][j].reInit(chunkValues[i][j]);
                mobGenerator.addNewPosition(tiles[i][j]);
            }
        }
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}