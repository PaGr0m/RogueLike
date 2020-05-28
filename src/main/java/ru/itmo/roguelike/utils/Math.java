package ru.itmo.roguelike.utils;

public class Math {
    public static double getRandomNumber(double minBound, double maxBound) {
        return java.lang.Math.random() * (maxBound - minBound) + minBound;
    }
}
