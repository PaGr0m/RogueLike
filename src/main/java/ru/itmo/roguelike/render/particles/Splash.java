package ru.itmo.roguelike.render.particles;

import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.utils.IntCoordinate;
import ru.itmo.roguelike.utils.MathUtils;

import java.awt.*;

public class Splash extends Particle {
    public Splash(IntCoordinate spawnPos, int intensity, Color color) {
        super(spawnPos);
        drawableDescriptor.setColor(color);
        setDrawer((Graphics2D graphics, int x, int y) -> {
            for (int i = 0; i < intensity; i++) {
                graphics.fillOval(
                        x + MathUtils.getRandomInt(0, 20 + time * 2),
                        y + MathUtils.getRandomInt(0, 20 + time * 2),
                        5, 5);
            }
        });
    }

    @Override
    public int getTTL() {
        return 10;
    }
}
