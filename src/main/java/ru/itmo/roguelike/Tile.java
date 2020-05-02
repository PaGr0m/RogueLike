package ru.itmo.roguelike;

public class Tile {
    private final float value;

    public Tile(float value) {
        // create tile from float
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
