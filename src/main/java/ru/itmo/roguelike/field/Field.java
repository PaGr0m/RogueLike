package ru.itmo.roguelike.field;

public class Field {
    private final int chunkNW, chunkNH;
    private final int marginX, marginY;

    private final NoiseGenerator generator;
    private final Chunk[][] field;

    private int shiftX, shiftY;

    public Field(int screenW, int screenH, int marginX, int marginY) {
        shiftX = 0;
        shiftY = 0;

        this.marginX = marginX;
        this.marginY = marginY;

        chunkNW = (int) Math.ceil((double) screenW / Chunk.WIDTH_IN_PIX + 2 * marginX);
        chunkNH = (int) Math.ceil((double) screenH / Chunk.HEIGHT_IN_PIX + 2 * marginY);

        generator = new NoiseGenerator(Chunk.WIDTH_IN_TILES, Chunk.HEIGHT_IN_TILES);
        field = new Chunk[chunkNW][chunkNH];

        for (int i = 0; i < chunkNW; ++i) {
            for (int j = 0; j < chunkNH; ++j) {
                int idxX = mod(i - marginX, chunkNW);
                int idxY = mod(j - marginY, chunkNH);
                field[idxX][idxY] = new Chunk(i - marginX, j - marginY, generator);
            }
        }
    }

    private static int mod(int n, int m) {
        if (n < 0) {
            n += (-n / m + 1) * m;
        }
        return n % m;
    }

    public TileType getTileType(int x, int y) {
        if (x < (shiftX - marginX) * Chunk.WIDTH_IN_PIX
                || y < (shiftY - marginY) * Chunk.HEIGHT_IN_PIX
                || x > (shiftX - marginX + chunkNW) * Chunk.WIDTH_IN_PIX
                || y > (shiftY - marginY + chunkNH) * Chunk.HEIGHT_IN_PIX
        ) {
            return TileType.BADROCK;
        }
        int chunkX = mod(Math.floorDiv(x, Chunk.WIDTH_IN_PIX), chunkNW);
        int chunkY = mod(Math.floorDiv(y, Chunk.HEIGHT_IN_PIX), chunkNH);
        int nextCoordX = (x - (shiftX - marginX) * Chunk.WIDTH_IN_PIX) % Chunk.WIDTH_IN_PIX;
        int nextCoordY = (y - Chunk.HEIGHT_IN_PIX * (shiftY - marginY)) % Chunk.HEIGHT_IN_PIX;
        return field[chunkX][chunkY].getTileType(nextCoordX, nextCoordY);
    }

    public void process(int centerX, int centerY) {
        double dx = (double) centerX / Chunk.WIDTH_IN_PIX - shiftX + marginX - chunkNW / 2.;
        double dy = (double) centerY / Chunk.HEIGHT_IN_PIX - shiftY + marginY - chunkNH / 2.;

        boolean moveRight = dx > 1;
        boolean moveLeft = dx < -1;
        boolean moveUp = dy < -1;
        boolean moveDown = dy > 1;

        if (moveLeft || moveRight) {
            int right = shiftX - marginX + chunkNW - 1;
            int left = shiftX - marginX;

            int from = mod(moveLeft ? right : left, chunkNW);
            int to = moveLeft ? left - 1 : right + 1;

            for (int i = 0; i < chunkNH; ++i) {
                field[from][i].reInitTiles(to, field[from][i].getY(), generator);
            }

            shiftX += moveLeft ? -1 : 1;
        }

        if (moveUp || moveDown) {
            int down = shiftY - marginY + chunkNH - 1;
            int up = shiftY - marginY;

            int from = mod(moveUp ? down : up, chunkNH);
            int to = moveUp ? up - 1 : down + 1;

            for (int i = 0; i < chunkNW; ++i) {
                field[i][from].reInitTiles(field[i][from].getX(), to, generator);
            }
            shiftY += moveUp ? -1 : 1;
        }
    }
}
