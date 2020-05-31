package ru.itmo.roguelike.manager.uimanager;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.inventory.Inventory;

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

    private final Player player;

    private static final int XP_BAR_LENGTH = 300;

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
                String.format("XP", player.getExp()),
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

        renderInventoty(graphics, player.getInventory());

    }

    private int getXPBarSize() {
        return (int) (300 * player.getExp() / player.getMaxExp());
    }

    private int getXPBarYCoordinate() {
        return 230 + (300 - getXPBarSize());
    }

    /**
     * Render inventory and available items
     */
    public void renderInventoty(@NotNull Graphics2D graphics, Inventory inventory) {
        // FIXME: magic numbers
        int startX = 80;
        int startY = 500;
        final int inventoryWidth = 700;
        final int inventoryHeight = 50;
        final int separatorWidth = 5;
        final int widthWithoutSeparators = inventoryWidth - separatorWidth * (inventory.getInventorySize() - 1);
        final int inventoryCellSize = widthWithoutSeparators / (inventory.getInventorySize());

        graphics.setColor(Color.BLACK);
        graphics.drawRect(startX, startY, inventoryWidth, inventoryHeight);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(startX, startY, inventoryWidth, inventoryHeight);
        drawInventorySeparators(graphics, startX, startY, inventoryHeight, inventoryCellSize, separatorWidth, inventory.getInventorySize());

        for (int i = 1; i < inventory.getInventorySize(); ++i) {
            if (inventory.getItem(i).isPresent()) {
                int x = startX + separatorWidth + (i - 1) * (separatorWidth + inventoryCellSize);
                drawSmth(graphics, x, startY, Color.DARK_GRAY);
            }
        }
    }

    /**
     * Draw vertical separators to inventory
     */
    private void drawInventorySeparators(@NotNull Graphics2D graphics, int startX, int startY, int height, int itemSize, int separatorWidth, int inventorySize) {
        final Color separatorColor = Color.BLACK;
        graphics.setColor(separatorColor);
        for (int i = 0; i < inventorySize - 1; i++) {
            int x = startX + i * (itemSize + separatorWidth);
            graphics.fillRect(x, startY, separatorWidth, height);
        }
    }

    /**
     * Temporary function for testing
     *
     * @param graphics
     * @param x
     * @param y
     */
    private void drawSmth(Graphics2D graphics, int x, int y, Color color) {
        graphics.setColor(color);
        graphics.fillRect(x + 2, y + 2, 40, 40);

    }
}
