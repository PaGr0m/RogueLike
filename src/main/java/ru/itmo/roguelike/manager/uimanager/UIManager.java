package ru.itmo.roguelike.manager.uimanager;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Player;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

public class UIManager {

    private final static Font MAIN_TEXT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 35);
    private final static Font SECONDARY_TEXT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 25);

    private final static Stroke MAIN_TEXT_STROKE = new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private final static Stroke SECONDARY_TEXT_STROKE = new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    public UIManager() {
    }

    public static void addStatusBar(@NotNull Graphics2D graphics) {
        // FIXME: magic numbers
        int height = 30;
        int width = 10;
        int delta = 20;

        AffineTransform transform = graphics.getTransform();
        transform.translate(width, height);

        TextLayout statusTL = new TextLayout("Status", MAIN_TEXT_FONT, graphics.getFontRenderContext());
        TextLayout hpTL = new TextLayout(
                String.format("HP: %d", Player.getPlayer().getHp()),
                SECONDARY_TEXT_FONT,
                graphics.getFontRenderContext()
        );
        TextLayout attackTL = new TextLayout(
                "Attack: ",
                SECONDARY_TEXT_FONT,
                graphics.getFontRenderContext()
        );

        graphics.setColor(Color.BLACK);
        graphics.setStroke(MAIN_TEXT_STROKE);
        graphics.draw(statusTL.getOutline(transform));
        transform.translate(0, delta);
        graphics.setStroke(SECONDARY_TEXT_STROKE);
        graphics.draw(hpTL.getOutline(transform));
        transform.translate(0, delta);
        graphics.draw(attackTL.getOutline(transform));

        graphics.setColor(Color.WHITE);
        statusTL.draw(graphics, width, height);
        hpTL.draw(graphics, width, height + delta);
        attackTL.draw(graphics, width, height + 2 * delta);
    }
}
