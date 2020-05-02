package ru.itmo.roguelike.map;

import ru.itmo.roguelike.Drawable;
import ru.itmo.roguelike.Tile;

public class Chunk implements Drawable {
    private static final int WIDTH = 5;
    private static final int HEIGHT = 5;
    private static final float[][] chunkValues = new float[WIDTH][HEIGHT];
    // Single thread only!

    private final Tile[][] tiles;

    public Chunk(int x, int y, NoiseGenerator generator) {
        generator.generate(x, y, chunkValues);
        tiles = new Tile[chunkValues.length][chunkValues[0].length];
        for (int i = 0; i < chunkValues.length; i++) {
            for (int j = 0; j < chunkValues[0].length; j++) {
                tiles[i][j] = new Tile(chunkValues[i][j]);
            }
        }
    }

    // draw

    public Tile[][] getTiles() {
        return tiles;
    }

}
