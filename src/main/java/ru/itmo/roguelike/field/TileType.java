package ru.itmo.roguelike.field;

import ru.itmo.roguelike.utils.Pair;

import java.awt.*;
import java.util.function.Function;

public enum TileType {
    ROCK(Color.GRAY, 0.5f, i -> i),
    GRASS(Color.GREEN, 0.3f,
            i -> 0.3f + getWithThresh(1f - i, 0.2f, 0.7f)),
    WATER(Color.BLUE, 0.0f, i -> i),
    BADROCK(null, -1, null);

    private final float threshold;
    private final Function<Float, Float> postprocess;
    private final Color mainColor;

    TileType(Color mainColor, float threshold, Function<Float, Float> postprocess) {
        this.mainColor = mainColor;
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

    public boolean isSolid() {
        return this == ROCK || this == BADROCK;
    }

    public Color getMainColor() {
        return mainColor;
    }

}
