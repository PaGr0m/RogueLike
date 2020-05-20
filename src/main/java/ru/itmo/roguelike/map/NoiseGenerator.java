package ru.itmo.roguelike.map;

public final class NoiseGenerator {
    private static final int MARGIN = 8;
    private static final int DIV_FACTOR = 8;

    private final float[][] noise;
    private final float[][] smooshed;

    public NoiseGenerator(int width, int height) {
        noise = new float[width / DIV_FACTOR + MARGIN][height / DIV_FACTOR + MARGIN];
        smooshed = new float[width / DIV_FACTOR + MARGIN][height / DIV_FACTOR + MARGIN];
    }

    private static float noise2d(int x, int y) {
        long v = x * 117 + y;
        v = (v << 13) ^ v;
        return Math.abs(1.0f - ((v * (v * v * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0f);
    }

    private static void genNoise2d(int x, int y, float[][] noise) {
        int width = noise.length - MARGIN;
        int height = noise[0].length - MARGIN;
        for (int i = 0; i < noise.length; i++) {
            for (int j = 0; j < noise[0].length; j++) {
                noise[i][j] = noise2d(x * width + i, y * height + j);
            }
        }
    }

    private static float getNoise2d(int x, int y, int i, int j, float[][] noise) {
        if (i < 0 || j < 0 || i >= noise.length || j >= noise[0].length) {
            return noise2d(x * (noise.length - MARGIN) + i, y * (noise[0].length - MARGIN) + j);
        }
        return noise[i][j];
    }

    private static void smoothNoise2D(int x, int y, float[][] noise, float[][] smooshed) {
        for (int i = 0; i < noise.length; i++) {
            for (int j = 0; j < noise[0].length; j++) {
                float corners = (getNoise2d(x, y, i - 1, j - 1, noise) +
                                 getNoise2d(x, y, i + 1, j - 1, noise) +
                                 getNoise2d(x, y, i - 1, j + 1, noise) +
                                 getNoise2d(x, y, i + 1, j + 1, noise)) / 16f;
                float sides = (getNoise2d(x, y, i - 1, j, noise) +
                               getNoise2d(x, y, i + 1, j, noise) +
                               getNoise2d(x, y, i, j - 1, noise) +
                               getNoise2d(x, y, i, j + 1, noise)) / 8f;
                float center = getNoise2d(x, y, i, j, noise) / 4;
                smooshed[i][j] = corners + sides + center;
            }
        }
    }

    private static float cosInterpolate(float a, float b, float x) {
        float ft = x * 3.1415927f;
        float f = (1 - (float) Math.cos(ft)) * 0.5f;
        return a * (1 - f) + b * f;
    }

    private void fill(int x, int y, float[][] map) {
        genNoise2d(x, y, noise);
        smoothNoise2D(x, y, noise, smooshed);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int px = i / DIV_FACTOR + 1, py = j / DIV_FACTOR + 1;
                float p1 = cosInterpolate(smooshed[px][py],
                                          smooshed[px + 1][py],
                                          i % DIV_FACTOR / (float) DIV_FACTOR);
                float p2 = cosInterpolate(smooshed[px][py + 1],
                                          smooshed[px + 1][py + 1],
                                          i % DIV_FACTOR / (float) DIV_FACTOR);
                map[i][j] = cosInterpolate(p1, p2, j % DIV_FACTOR / (float) DIV_FACTOR);
            }
        }
    }

    /**
     * Generates ValueNoise {@see "https://habr.com/ru/post/142592/"}
     **/
    public void generate(int i, int j, float[][] field) {
        fill(i, j, field);
    }
}
