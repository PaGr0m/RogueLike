package ru.itmo.roguelike.utils;

import java.util.Random;

public class MathUtils {
    private static final Random random = new Random();

    /**
     * Sample one int from Uniform([min, max])
     */
    public static int getRandomInt(int minBound, int maxBound) {
        return random.nextInt() * (maxBound - minBound) + minBound;
    }

    /**
     * Sample one double from Uniform([min, max])
     */
    public static double getRandomDouble(double minBound, double maxBound) {
        return random.nextDouble() * (maxBound - minBound) + minBound;
    }
}
