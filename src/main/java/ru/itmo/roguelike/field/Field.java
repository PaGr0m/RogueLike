package ru.itmo.roguelike.field;

import ru.itmo.roguelike.characters.Player;

public interface Field {
    void reInit(int posX, int posY);

    TileType getTileType(int x, int y);

    void process(int centerX, int centerY);

    void setDefaultPosToPlayer(Player p);
}
