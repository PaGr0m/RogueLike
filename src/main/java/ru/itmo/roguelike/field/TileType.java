package ru.itmo.roguelike.field;

import ru.itmo.roguelike.utils.Pair;

import java.awt.*;
import java.util.function.Function;

/**
 * Type of {@link Tile}
 */
public enum TileType {
    ROCK(Color.GRAY, 0.5f, i -> i),
    GRASS(Color.GREEN, 0.3f,
            i -> 0.3f + getWithThresh(1f - i, 0.2f, 0.7f)),
    WATER(Color.BLUE, 0.0f, i -> i),
    BEDROCK(Color.WHITE, -1, null);

    private final float threshold;
    private final Function<Float, Float> postprocess;
    private final Color mainColor;

    TileType(Color mainColor, float threshold, Function<Float, Float> postprocess) {
        this.mainColor = mainColor;
        this.threshold = threshold;
        this.postprocess = postprocess;
    }

    private static float getWithThresh(float val, float min, float max) {
        return Math.max(Math.min(val, max), min);
    }

    /**
     * <p>Evaluates tile type and its intensity from single float value.</p>
     *
     * <p>
     * The segment [0, 1] is divided into parts [threshold - prevType.threshold, threshold],
     * each of which corresponds to its type.</p>
     * <p>
     * Intensity is a real number in range [0, 1], calculated by {@code value} in its own way for each type.
     * </p>
     *
     * @param value real number in range [0, 1]
     * @return The type corresponds to the {@code value} and its intensity
     */
    public static Pair<TileType, Float> getTypeAndIntensity(float value) {
        int index = TileType.values().length - 1;
        for (TileType type : values()) {
            if (value > type.threshold) {
                index = type.ordinal();
                break;
            }

        }

        TileType type = TileType.values()[index];

        if (type == BEDROCK) {
            return new Pair<>(BEDROCK, 1f);
        }

        float interval = (index > 0 ? TileType.values()[index - 1].threshold : 1f) - type.threshold;
        return new Pair<>(type, type.postprocess.apply((value - type.threshold) / interval));
    }

    public boolean isSolid() {
        return this == ROCK || this == BEDROCK;
    }

    /**
     * @return main color of tile with this type
     */
    public Color getMainColor() {
        return mainColor;
    }

}
