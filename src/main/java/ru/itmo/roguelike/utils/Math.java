package ru.itmo.roguelike.utils;

public class Math {
    /**
     * Return random double in specified boundaries
     *
     * @param minBound
     * @param maxBound
     * @return random number in [minBound, maxBound]
     */
    public static double getRandomNumber(double minBound, double maxBound) {
        return java.lang.Math.random() * (maxBound - minBound) + minBound;
    }
}
