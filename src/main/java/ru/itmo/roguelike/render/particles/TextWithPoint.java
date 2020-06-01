package ru.itmo.roguelike.render.particles;

import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;

public class TextWithPoint extends Particle {
    private final static int TTL = 50;
    private final static Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);

    public TextWithPoint(IntCoordinate position, String text, Color color) {
        super(position);
        drawableDescriptor.setColor(color);
        setDrawer((Graphics2D graphics, int x, int y) -> {
            graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, Math.min(time + 1, 14)));
            int dy = TTL / 2 - time / 2;
            graphics.drawString(text, x, y - 30);
            graphics.drawLine(x, y - 28 - dy, x, y - dy);
            graphics.drawLine(x - 10, y - 10 - dy, x, y - dy);
            graphics.drawLine(x + 10, y - 10 - dy, x, y - dy);
        });
    }

    @Override
    public int getTTL() {
        return TTL;
    }
}
