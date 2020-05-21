package ru.itmo.roguelike.manager.uimanager;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

public class UIManager {

    private final static Font MAIN_TEXT_FONT = new Font("Courier New", Font.BOLD, 24);
    private final static Font SECONDARY_TEXT_FONT = new Font("Courier New", Font.BOLD, 12);

    private final static Stroke MAIN_TEXT_STROKE = new BasicStroke(3);
    private final static Stroke SECONDARY_TEXT_STROKE = new BasicStroke(3);

    public UIManager() {}

    public static void addStatusBar(Graphics2D graphics) {
        // FIXME: magic numbers
        int height = 30;
        int width = 10;
        int delta = 20;

        AffineTransform transform = graphics.getTransform();
        transform.translate(width, height);

        graphics.setStroke(MAIN_TEXT_STROKE);
        TextLayout statusTL = new TextLayout("Status", MAIN_TEXT_FONT, graphics.getFontRenderContext());
        TextLayout hpTL = new TextLayout("HP: ", SECONDARY_TEXT_FONT, graphics.getFontRenderContext());
        TextLayout mpTL = new TextLayout("MP: ", SECONDARY_TEXT_FONT, graphics.getFontRenderContext());

        graphics.setColor(Color.BLACK);
        graphics.draw(statusTL.getOutline(transform));
        transform.translate(0, delta);
        graphics.draw(hpTL.getOutline(transform));
        transform.translate(0, delta);
        graphics.draw(mpTL.getOutline(transform));

        graphics.setColor(Color.WHITE);
        statusTL.draw(graphics, width, height);
        hpTL.draw(graphics, width, height + delta);
        mpTL.draw(graphics, width, height + 2 * delta);
    }
}
