package ru.itmo.roguelike.map;

import ru.itmo.roguelike.utils.Pair;

import java.awt.*;
import java.util.function.Function;

public enum TileType {
    ROCK(Color.GRAY, true, 0.5f, i -> i),
    GRASS(Color.GREEN, false, 0.3f, i -> getWithThresh(1f - i, 0.2f, 1f)),
    WATER(Color.BLUE, false, 0.0f, i -> i);

    private final Color mainColor;
    private final boolean isSolid;
    private final float threshold;
    private final Function<Float, Float> postprocess;

    TileType(Color mainColor, boolean isSolid, float threshold, Function<Float, Float> postprocess) {
        this.mainColor = mainColor;
        this.isSolid = isSolid;
        this.threshold = threshold;
        this.postprocess = postprocess;
    }

    private static float getWithThresh(float val, float min, float max) {
        if (val > max) return max;
        if (val < min) return min;
        return val;
    }

    public static Pair<TileType, Float> getTypeAndIntensity(float value) {
        int index = TileType.values().length - 1;
        for (TileType type : values()) {
            if (value > type.threshold) {
                index = type.ordinal();
                break;
            }

        }

        TileType type = TileType.values()[index];
        float interval = (index > 0 ? TileType.values()[index - 1].threshold : 1f) - type.threshold;
        return new Pair<>(type, type.postprocess.apply((value - type.threshold) / interval));
    }

    public Color getMainColor() {
        return mainColor;
    }

    public boolean isSolid() {
        return isSolid;
    }
}
