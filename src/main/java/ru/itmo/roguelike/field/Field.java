package ru.itmo.roguelike.field;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.geom.Rectangle2D;
import java.util.Optional;

/**
 * The main game field. It represents a world man where all objects are located.
 * It divided to tiles ({@link Tile}), where all tiles may be free or solid.
 * Actors ({@link ru.itmo.roguelike.characters.Actor}) may move only on free tiles
 */
public interface Field {
    /**
     * Reinitialize at given coordinate
     */
    void reInit(IntCoordinate center);

    /**
     * @return tile at a given coordinate. Returns {@code Optional.empty()}
     * if there is no tile at the given coordinate at the moment.
     */
    Optional<Tile> getTile(IntCoordinate coordinate);

    /**
     * @return tile type a given coordinate. Returns {@link TileType#BEDROCK}
     * if there is no tile at the given coordinate at the moment.
     */
    default TileType getTileType(IntCoordinate coordinate) {
        return getTile(coordinate).map(Tile::getType).orElse(TileType.BEDROCK);
    }

    default TileType[][] getAreaTileType(@NotNull Rectangle2D boundingBox) {
        int width = (int) boundingBox.getWidth();
        int height = (int) boundingBox.getHeight();

        TileType[][] result = new TileType[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                IntCoordinate coordinate = new IntCoordinate((int) boundingBox.getMinX(), (int) boundingBox.getMinY());
                coordinate.add(new IntCoordinate(i, j));

                result[i][j] = getTileType(coordinate);
            }
        }

        return result;
    }

    /**
     * Updates field after moving the camera
     *
     * @param centerCoordinate coordinate of the camera center
     */
    void process(IntCoordinate centerCoordinate);

    /**
     * Change the player’s position to default for this field.
     */
    void setDefaultPosToPlayer(Player p);
}
