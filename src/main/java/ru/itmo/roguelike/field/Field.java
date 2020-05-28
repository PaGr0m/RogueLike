package ru.itmo.roguelike.field;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.utils.IntCoordinate;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface Field {
    void reInit(int posX, int posY);

    Optional<Tile> getTile(IntCoordinate coordinate);

    default TileType getTileType(IntCoordinate coordinate) {
        return getTile(coordinate).map(Tile::getType).orElse(TileType.BADROCK);
    }

    void process(IntCoordinate centerCoordinate);

    void setDefaultPosToPlayer(Player p);
}
