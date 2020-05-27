package ru.itmo.roguelike.field;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;

public class FiniteField implements Field {
    private Tile[][] field;
    private int shiftX;
    private int shiftY;

    public FiniteField(Path file) {
        try (
                ObjectInputStream reader =
                        new ObjectInputStream(new BufferedInputStream(new FileInputStream(file.toFile())))
        ) {
            int width = reader.readInt();
            int height = reader.readInt();

            shiftX = width / 2;
            shiftY = height / 2;

            field = new Tile[width][height];

            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    field[i][j] = new Tile(reader.readFloat());
                    field[i][j].setXY(i + shiftX, j + shiftY);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void reInit(int posX, int posY) {
    }

    @Override
    public TileType getTileType(int x, int y) {
        int xIdx = x / Tile.WIDTH_IN_PIX - shiftX;
        int yIdx = y / Tile.HEIGHT_IN_PIX - shiftY;

        if (xIdx < 0 || xIdx >= field.length || yIdx < 0 || yIdx >= field[0].length) {
            return TileType.BADROCK;
        }
        return field[xIdx][yIdx].getType();
    }

    @Override
    public void process(int centerX, int centerY) {
    }

    @Override
    public void setDefaultPosToPlayer(Player p) {
        p.setCoordinate(new IntCoordinate(2, 0));
    }
}
