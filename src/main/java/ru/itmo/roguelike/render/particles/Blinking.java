package ru.itmo.roguelike.render.particles;


import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;

public class Blinking extends Particle {
    private int ttl = 10;


    /**
     * @param position particle spawn position
     */
    public Blinking(IntCoordinate position) {
        super(position);
        setDrawer((graphics, x, y) -> {
            for (int i = 0; i < 360; i += 30) {
                int delta = (ttl + (i / 16) * 10) % 50;

                int start = Math.max(10, delta);
                int end = Math.min(20, delta + 4);

                if (start < end) {
                    double angle = Math.PI * i / 180;

                    int x1 = x + (int) (Math.cos(angle) * start);
                    int y1 = y + (int) (Math.sin(angle) * start);

                    int x2 = x + (int) (Math.cos(angle) * end);
                    int y2 = y + (int) (Math.sin(angle) * end);

                    graphics.setColor(Color.YELLOW);

                    graphics.drawLine(x1, y1, x2, y2);
                }
            }
        });
    }

    public void destroy() {
        unregister(this);
        ttl = -1;
    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        super.draw(graphics, camera);
        ttl++;
    }

    @Override
    public int getTTL() {
        return Integer.MAX_VALUE;
    }

    public void setPosition(IntCoordinate position) {
        drawableDescriptor.setPosition(position);
    }

}
