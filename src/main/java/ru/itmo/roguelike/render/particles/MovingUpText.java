package ru.itmo.roguelike.render.particles;

import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;

/**
 * Text that slowly floats up.
 */
public class MovingUpText extends Particle {
    private final static Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);

    public MovingUpText(IntCoordinate position, String text, Color color) {
        super(position);

        String[] words = text.split("\\n");

        drawableDescriptor.setColor(color);
        setDrawer((Graphics2D graphics, int x, int y) -> {
            graphics.setFont(FONT);

            int wordNum = 0;
            for (String word : words) {
                graphics.drawString(word, x, y - time / 2 + wordNum * FONT.getSize());
                wordNum++;
            }
        });
    }

    @Override
    public int getTTL() {
        return 100;
    }
}
