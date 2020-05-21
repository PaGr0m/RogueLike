package ru.itmo.roguelike.map;

import java.awt.*;

public enum TileType {
    ROCK(Color.GRAY, true),
    GRASS(Color.GREEN, false),
    WATER(Color.BLUE, false);

    private final Color mainColor;
    private final boolean isSolid;

    TileType(Color mainColor, boolean isSolid) {
        this.mainColor = mainColor;
        this.isSolid = isSolid;
    }

    public Color getMainColor() {
        return mainColor;
    }

    public boolean isSolid() {
        return isSolid;
    }
}
