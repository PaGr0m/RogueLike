package ru.itmo.roguelike.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

import static org.junit.Assert.*;

public class FloatCoordinateTest {

    @Test
    public void testAngle() {
        FloatCoordinate[] coordinates = {
                new FloatCoordinate(1, 0),
                new FloatCoordinate(1, 1),
                new FloatCoordinate(0, 1),
                new FloatCoordinate(-1, 1),
                new FloatCoordinate(-1, 0),
                new FloatCoordinate(-1, -1),
                new FloatCoordinate(0, -1),
                new FloatCoordinate(1, -1)
        };
        float pi = (float) Math.PI;

        float[] expects = {0, pi/4, pi/2, 3*pi/4, pi, -3*pi/4, -pi/2, -pi/4};

        for (int i = 0; i < 8; ++i) {
            assertEquals(expects[i], coordinates[i].toAngle(), 1e-4);
        }

        assertEquals(0, FloatCoordinate.getZeroPosition().toAngle(), 1e-5);
    }

    @Test
    public void testFromAngle() {
        FloatCoordinate[] coordinates = {
                new FloatCoordinate(1, 0),
                new FloatCoordinate(1, 1),
                new FloatCoordinate(0, 1),
                new FloatCoordinate(-1, 1),
                new FloatCoordinate(-1, 0),
                new FloatCoordinate(-1, -1),
                new FloatCoordinate(0, -1),
                new FloatCoordinate(1, -1)
        };

        UnaryOperator<Float> modulo2Pi = f -> f < 0 ? f + 2 * (float) Math.PI : f;

        for (FloatCoordinate coordinate : coordinates) {
            assertEquals(modulo2Pi.apply(coordinate.toAngle()),
                    modulo2Pi.apply(FloatCoordinate.fromAngle(coordinate.toAngle()).toAngle()), 1e-5);
        }
    }
}