package ru.itmo.roguelike.manager.uimanager;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Player;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

@Singleton
public class UIManager {

    private final static Font MAIN_TEXT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 35);
    private final static Font SECONDARY_TEXT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 25);

    private final static Stroke MAIN_TEXT_STROKE = new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private final static Stroke SECONDARY_TEXT_STROKE = new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private static final int XP_BAR_LENGTH = 300;
    private final Player player;

    @Inject
    public UIManager(Player player) {
        this.player = player;
    }

    public void renderStatusBar(@NotNull Graphics2D graphics) {
        // FIXME: magic numbers
        int height = 30;
        int width = 10;
        int delta = 20;

        AffineTransform transform = graphics.getTransform();
        transform.translate(width, height);

        TextLayout statusTL = new TextLayout("Status", MAIN_TEXT_FONT, graphics.getFontRenderContext());
        TextLayout hpTL = new TextLayout(
                String.format("HP: %d", player.getHp()),
                SECONDARY_TEXT_FONT,
                graphics.getFontRenderContext()
        );
        TextLayout levelTl = new TextLayout(
                String.format("Level: %d", player.getLevel()),
                SECONDARY_TEXT_FONT,
                graphics.getFontRenderContext()
        );
        TextLayout expTl = new TextLayout(
                "XP",
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
        graphics.draw(levelTl.getOutline(transform));


        graphics.setColor(Color.WHITE);
        statusTL.draw(graphics, width, height);
        hpTL.draw(graphics, width, height + delta);
        levelTl.draw(graphics, width, height + 2 * delta);


        //FIXME: Magic numbers
        graphics.setColor(Color.BLACK);
        graphics.drawRect(10, 230, 10, XP_BAR_LENGTH);
        transform.translate(0, 490);
        graphics.draw(expTl.getOutline(transform));
        graphics.setColor(Color.WHITE);
        graphics.fillRect(10, 230, 10, XP_BAR_LENGTH);
        expTl.draw(graphics, 10, 560);
        graphics.setColor(Color.magenta);
        graphics.fillRect(10, getXPBarYCoordinate(), 10, getXPBarSize());

    }

    private int getXPBarSize() {
        return (int) (300 * player.getExp() / player.getMaxExp());
    }

    private int getXPBarYCoordinate() {
        return 230 + (300 - getXPBarSize());
    }
}
