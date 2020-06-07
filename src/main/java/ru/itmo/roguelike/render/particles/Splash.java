package ru.itmo.roguelike.render.particles;

import ru.itmo.roguelike.utils.IntCoordinate;
import ru.itmo.roguelike.utils.MathUtils;

import java.awt.*;
import java.util.function.IntUnaryOperator;

/**
 * Random splash in all directions
 */
public class Splash extends Particle {
    private static final int TTL = 10;

    /**
     * Fabric method to create Splash effect and properly register it in internal drawing system.
     *
     * @param spawnPos  position of the effect
     * @param intensity how many splash particles will be rendered
     * @param color     color of the effect
     */
    public static void createSplashAndRegister(IntCoordinate spawnPos, int intensity, Color color) {
        new Splash(spawnPos, intensity, color);
    }

    /**
     * Fabric method to create Splash effect and properly register it in internal drawing system.
     *
     * @param spawnPos    position of the effect
     * @param intensity   how many splash particles will be rendered
     * @param color       color of the effect
     * @param getMaxDelta function that according to amount of game steps passed determines, how wide will particles
     */
    public static void createSplashAndRegister(
            IntCoordinate spawnPos,
            int intensity,
            Color color,
            IntUnaryOperator getMaxDelta
    ) {
        new Splash(spawnPos, intensity, color, getMaxDelta);
    }

    private Splash(IntCoordinate spawnPos, int intensity, Color color) {
        this(spawnPos, intensity, color, (int time) -> time + 20);
    }

    private Splash(IntCoordinate spawnPos, int intensity, Color color, IntUnaryOperator getMaxDelta) {
        super(spawnPos);
        drawableDescriptor.setColor(color);
        setDrawer((Graphics2D graphics, int x, int y) -> {
            for (int i = 0; i < intensity; i++) {
                int size = Math.min(TTL + 1 - time, 5);
                int delta = getMaxDelta.applyAsInt(time);
                graphics.fillOval(
                        x + MathUtils.getRandomInt(0, 1 + delta * 2) - delta,
                        y + MathUtils.getRandomInt(0, 1 + delta * 2) - delta,
                        size, size);
            }
        });
    }

    @Override
    public int getTTL() {
        return TTL;
    }
}
