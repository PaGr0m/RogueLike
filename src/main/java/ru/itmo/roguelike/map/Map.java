package ru.itmo.roguelike.map;

public class Map {
    private final int chunkNW, chunkNH;

    private final NoiseGenerator generator;
    private final Chunk[][] field;

    private int shiftX, shiftY;

    public Map(int screenW, int screenH, int marginX, int marginY) {
        shiftX = -marginX;
        shiftY = -marginY;

        chunkNW = screenW / Chunk.WIDTH_IN_PIX + 2 * marginX;
        chunkNH = screenH / Chunk.HEIGHT_IN_PIX + 2 * marginY;

        generator = new NoiseGenerator(Chunk.WIDTH_IN_TILES, Chunk.HEIGHT_IN_TILES);
        field = new Chunk[chunkNW][chunkNH];

        for (int i = 0; i < chunkNW; ++i) {
            for (int j = 0; j < chunkNH; ++j) {
                field[i][j] = new Chunk(i - marginX, j - marginY, generator);
            }
        }
    }

    private static int mod(int n, int m) {
        if (n < 0) {
            n += (-n / m + 1) * m;
        }
        return n % m;
    }

    public void process(int centerX, int centerY) {
        double dx = (double) centerX / Chunk.WIDTH_IN_PIX - shiftX - chunkNW / 2.;
        double dy = (double) centerY / Chunk.HEIGHT_IN_PIX - shiftY - chunkNH / 2.;

        boolean moveRight = dx > 1;
        boolean moveLeft = dx < -1;
        boolean moveUp = dy < -1;
        boolean moveDown = dy > 1;

        if (moveLeft || moveRight) {
            int right = shiftX + chunkNW - 1;
            int left = shiftX;

            int from = mod(moveLeft ? right : left, chunkNW);
            int to = moveLeft ? left - 1 : right + 1;

            for (int i = 0; i < chunkNH; ++i) {
                field[from][i].reInitTiles(to, field[from][i].getY(), generator);
            }

            shiftX += moveLeft ? -1 : 1;
        }

        if (moveUp || moveDown) {
            int down = shiftY + chunkNH - 1;
            int up = shiftY;

            int from = mod(moveUp ? down : up, chunkNH);
            int to = moveUp ? up - 1 : down + 1;

            for (int i = 0; i < chunkNW; ++i) {
                field[i][from].reInitTiles(field[i][from].getX(), to, generator);
            }
            shiftY += moveUp ? -1 : 1;
        }
    }
}
