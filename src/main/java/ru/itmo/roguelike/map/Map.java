package ru.itmo.roguelike.map;

import ru.itmo.roguelike.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Map implements Drawable {
    private final int chunkNW, chunkNH;
    private final int chunkW, chunkH;

    private final NoiseGenerator generator;

    // Tiles

    public Map(int chunkNW, int chunkNH, int chunkW, int chunkH) {
        this.chunkNW = chunkNW;
        this.chunkNH = chunkNH;
        this.chunkW = chunkW;
        this.chunkH = chunkH;

        generator = new NoiseGenerator(chunkW, chunkH);
    }

    // draw

    public void generateNext(int i, int j) {
        float[][] chunk = new float[chunkW][chunkH];
        generator.generate(i, j, chunk);
    }


}
