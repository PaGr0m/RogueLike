package ru.itmo.roguelike.field;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.utils.IntCoordinate;
import ru.itmo.roguelike.utils.MathUtils;

import java.util.Optional;

/**
 * Endless field generated using {@link NoiseGenerator}
 */
public class AutoGeneratedField implements Field {
    private final int chunkNW, chunkNH;
    private final int marginX, marginY;

    private final NoiseGenerator generator;
    private final Chunk[][] field;

    private int shiftX, shiftY;

    public AutoGeneratedField(int screenW, int screenH, int marginX, int marginY, MobPositionGenerator mobGenerator) {
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
                int idxX = Math.floorMod(shiftX + i - marginX, chunkNW);
                int idxY = Math.floorMod(shiftY + j - marginY, chunkNH);
                field[idxX][idxY] = new Chunk(shiftX + i - marginX, shiftY + j - marginY, generator, mobGenerator);
            }
        }
    }

    @Override
    public void reInit(IntCoordinate coordinate) {
        shiftX = coordinate.getX() / Chunk.WIDTH_IN_PIX - chunkNW / 2;
        shiftY = coordinate.getY() / Chunk.WIDTH_IN_PIX - chunkNH / 2;

        for (int i = 0; i < chunkNW; ++i) {
            for (int j = 0; j < chunkNH; ++j) {
                int idxX = Math.floorMod(shiftX + i - marginX, chunkNW);
                int idxY = Math.floorMod(shiftY + j - marginY, chunkNH);
                field[idxX][idxY].reInitTiles(shiftX + i - marginX, shiftY + j - marginY, generator);
            }
        }
    }

    @Override
    public Optional<Tile> getTile(IntCoordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        if (x < (shiftX - marginX) * Chunk.WIDTH_IN_PIX
                || y < (shiftY - marginY) * Chunk.HEIGHT_IN_PIX
                || x > (shiftX - marginX + chunkNW) * Chunk.WIDTH_IN_PIX
                || y > (shiftY - marginY + chunkNH) * Chunk.HEIGHT_IN_PIX
        ) {
            return Optional.empty();
        }
        int chunkX = Math.floorMod(Math.floorDiv(x, Chunk.WIDTH_IN_PIX), chunkNW);
        int chunkY = Math.floorMod(Math.floorDiv(y, Chunk.HEIGHT_IN_PIX), chunkNH);
        int nextCoordX = (x - (shiftX - marginX) * Chunk.WIDTH_IN_PIX) % Chunk.WIDTH_IN_PIX;
        int nextCoordY = (y - Chunk.HEIGHT_IN_PIX * (shiftY - marginY)) % Chunk.HEIGHT_IN_PIX;
        return Optional.of(field[chunkX][chunkY].getTile(nextCoordX, nextCoordY));
    }

    @Override
    public void process(IntCoordinate centerCoordinate) {
        double dx = (double) centerCoordinate.getX() / Chunk.WIDTH_IN_PIX - shiftX + marginX - chunkNW / 2.;
        double dy = (double) centerCoordinate.getY() / Chunk.HEIGHT_IN_PIX - shiftY + marginY - chunkNH / 2.;

        boolean moveRight = dx > 1;
        boolean moveLeft = dx < -1;
        boolean moveUp = dy < -1;
        boolean moveDown = dy > 1;

        if (moveLeft || moveRight) {
            int right = shiftX - marginX + chunkNW - 1;
            int left = shiftX - marginX;

            int from = Math.floorMod(moveLeft ? right : left, chunkNW);
            int to = moveLeft ? left - 1 : right + 1;

            for (int i = 0; i < chunkNH; ++i) {
                field[from][i].reInitTiles(to, field[from][i].getY(), generator);
            }

            shiftX += moveLeft ? -1 : 1;
        }

        if (moveUp || moveDown) {
            int down = shiftY - marginY + chunkNH - 1;
            int up = shiftY - marginY;

            int from = Math.floorMod(moveUp ? down : up, chunkNH);
            int to = moveUp ? up - 1 : down + 1;

            for (int i = 0; i < chunkNW; ++i) {
                field[i][from].reInitTiles(field[i][from].getX(), to, generator);
            }
            shiftY += moveUp ? -1 : 1;
        }
    }

    @Override
    public void setDefaultPosToPlayer(Player p) {
        int x = MathUtils.getRandomInt(-500_000, 500_000); // FIXME (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧
        int y = MathUtils.getRandomInt(-500_000, 500_000);
        p.setCoordinate(new IntCoordinate(x, y));
    }
}
