package ru.itmo.roguelike.map;

import ru.itmo.roguelike.Drawable;
import ru.itmo.roguelike.utils.Pair;

import java.util.HashMap;

import static java.lang.Math.abs;

public class Map implements Drawable {
    private final int radius, safeRadius;
    private final int chunkW, chunkH;
    private int cX;
    private int cY;

    private final NoiseGenerator generator;
    private final HashMap<Pair<Integer, Integer>, Chunk> map = new HashMap<>();

    // Tiles

    public Map(int radius, int safeRadius, int chunkW, int chunkH, int x, int y) {
        this.radius = radius;
        this.safeRadius = safeRadius;
        this.chunkW = chunkW;
        this.chunkH = chunkH;
        this.cX = x;
        this.cY = y;

        generator = new NoiseGenerator(chunkW, chunkH);
        regenerate();
    }

    // draw

    public void process(int x, int y) {
        if (Math.sqrt(x * x + y * y) > safeRadius) {
            cX = x;
            cY = y;
            regenerate();
        }
    }

    private void regenerate() {
        map.keySet().removeIf(this::isTooFar);
        for (int i = cX - radius; i <= cX + radius; i++) {
            for (int j = cY - radius; j <= cY + radius; ++j) {
                Pair<Integer, Integer> pos = new Pair<>(i, j);
                if (!isTooFar(pos)) {
                    if (!map.containsKey(pos)) {
                        map.put(pos, new Chunk(i, j, generator));
                    }
                }
            }
        }
    }

    private boolean isTooFar(Pair<Integer, Integer> pos) {
        int i = pos.getFirst(), j = pos.getSecond();
        return abs(i) + abs(j) > radius || abs(i) > radius || abs(j) > radius;
    }

    public void generateNext(int i, int j) {
        float[][] chunk = new float[chunkW][chunkH];
        generator.generate(i, j, chunk);
    }

    public HashMap<Pair<Integer, Integer>, Chunk> getChunks() {
        return map;
    }
}
