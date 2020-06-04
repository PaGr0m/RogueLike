package ru.itmo.roguelike.manager.uimanager;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.inventory.Inventory;
import ru.itmo.roguelike.manager.eventmanager.Event;
import ru.itmo.roguelike.manager.eventmanager.EventManager;
import ru.itmo.roguelike.utils.FileUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.awt.font.TextLayout;
import java.io.File;
import java.io.IOException;

@Singleton
public class UIManager {
    private static final Font FONT = getDefaultFont();
    public final static Font MAIN_TEXT_FONT = FONT.deriveFont(Font.PLAIN, 25);
    public final static Font SECONDARY_TEXT_FONT = FONT.deriveFont(Font.BOLD, 18);
    public final static Font THIRDARY_TEXT_FONT = FONT.deriveFont(Font.PLAIN, 20);
    private static final int BAR_Y_POSITION = 480;
    private static final int BAR_X_POSITION = 80;
    private static final int SCREEN_WIDTH = 800;
    private static final int PROGRESSBAR_HEIGHT = 20;
    private static final Color BG_COLOR = Color.LIGHT_GRAY;
    private static final int CELL_NUMBER_SIZE = 20;
    private static final int LVL_X = 30;
    private static final int LVL_Y = 30;
    private static final int LVL_WIDTH = 120;
    private static final int LVL_HEIGHT = 40;
    private static final int EVENT_SIZE = 40;
    private static final int EVENT_SEP = 10;
    private final Player player;
    private final EventManager eventManager;

    @Inject
    public UIManager(Player player, EventManager eventManager) {
        this.eventManager = eventManager;
        this.player = player;
    }

    private static Font getDefaultFont() {
        try {
            File file = FileUtils.getFile("fonts/minecraft.ttf");
            assert file != null;
            return Font.createFont(Font.TRUETYPE_FONT, file);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static void drawCenteredText(Graphics2D graphics, String text, int x, int y, Font font, Color color) {
        TextLayout textLayout = new TextLayout(text, font, graphics.getFontRenderContext());
        x -= textLayout.getBounds().getWidth() / 2 - 1;
        y += textLayout.getBounds().getHeight() / 2 + 1;
        graphics.setColor(color);
        textLayout.draw(graphics, x, y);
    }

    public void renderStatusBar(@NotNull Graphics2D graphics) {
        drawLevel(graphics);

        int y = BAR_Y_POSITION;
        int x = BAR_X_POSITION;
        int length = SCREEN_WIDTH - 2 * x;

        drawEvents(graphics, x + length - EVENT_SIZE, y - EVENT_SIZE - EVENT_SEP);

        drawProgressBar(graphics, x, y, length,
                String.format("HP : %d / %d", player.getHp(), player.getMaxHP()),
                Color.RED, (double) player.getHp() / player.getMaxHP());

        y += PROGRESSBAR_HEIGHT;

        renderInventory(graphics, x, y, length, PROGRESSBAR_HEIGHT * 2);

        y += PROGRESSBAR_HEIGHT * 2;

        drawProgressBar(graphics, x, y, length,
                String.format("XP : %.2f / %.2f", player.getExp(), player.getMaxExp()),
                Color.ORANGE, (double) player.getExp() / player.getMaxExp());

    }

    private void drawProgressBar(
            Graphics2D graphics, int x, int y, int len,
            String name,
            Color front, double progress
    ) {
        int progressVal = (int) (progress * len);

        TextLayout text = new TextLayout(name, SECONDARY_TEXT_FONT, graphics.getFontRenderContext());
        int textPosX = (int) (x + (len - text.getBounds().getWidth()) / 2);
        int textPosY = y + UIManager.PROGRESSBAR_HEIGHT - 2;

        graphics.setColor(BG_COLOR);
        graphics.fillRect(x, y, len, UIManager.PROGRESSBAR_HEIGHT);
        graphics.setColor(front);
        graphics.fillRect(x, y, progressVal, UIManager.PROGRESSBAR_HEIGHT);
        graphics.setColor(Color.BLACK);
        text.draw(graphics, textPosX, textPosY);
        graphics.drawRect(x, y, len, UIManager.PROGRESSBAR_HEIGHT);
    }

    /**
     * Render inventory and available items
     */
    public void renderInventory(@NotNull Graphics2D graphics, int x, int y, int len, int wid) {
        graphics.setColor(BG_COLOR);
        graphics.fillRect(x, y, len, wid);

        Inventory inventory = player.getInventory();
        float cellWidth = (float) len / inventory.getInventoryLength();

        int numberPosY = y + wid - CELL_NUMBER_SIZE;
        for (int i = 0; i < inventory.getInventoryLength(); i++) {
            int posX = x + (int) (i * cellWidth);

            graphics.setColor(Color.BLACK);
            graphics.drawLine(posX, y, posX, y + wid);

            inventory.getItem(i).ifPresent(
                    item -> item.renderInInventory(graphics, posX, y, (int) cellWidth, wid)
            );

            int numberPosX = posX + (int) cellWidth - CELL_NUMBER_SIZE;
            graphics.setColor(Color.BLACK);
            graphics.fillRect(numberPosX, numberPosY, CELL_NUMBER_SIZE, CELL_NUMBER_SIZE);
            drawCenteredText(graphics, Integer.toString(i + 1),
                    numberPosX + CELL_NUMBER_SIZE / 2 + 1, numberPosY + CELL_NUMBER_SIZE / 2 + 1,
                    THIRDARY_TEXT_FONT, Color.ORANGE);
        }

        graphics.setColor(Color.BLACK);
        graphics.drawRect(x, y, len, wid);
    }

    public void drawLevel(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(LVL_X, LVL_Y, LVL_WIDTH, LVL_HEIGHT);
        graphics.setColor(Color.YELLOW);
        drawCenteredText(graphics, "LVL " + player.getLevel(),
                LVL_X + LVL_WIDTH / 2, LVL_Y + LVL_HEIGHT / 2,
                MAIN_TEXT_FONT, Color.YELLOW
        );
    }

    public void drawEvents(Graphics2D graphics, int x, int y) {
        for (Event event : eventManager.getDrawableEvents()) {
            Stroke oldStroke = graphics.getStroke();
            int angle = (int) ((float) event.getCurr() * 360 / event.getDuration());

            graphics.setColor(event.getColor());
            graphics.fillOval(x, y, EVENT_SIZE, EVENT_SIZE);

            event.draw(graphics, x + EVENT_SIZE / 2, y + EVENT_SIZE / 2);

            graphics.setStroke(new BasicStroke(10));
            graphics.setColor(Color.RED);
            graphics.drawArc(x + 5, y + 5, EVENT_SIZE - 9, EVENT_SIZE - 9, 90, angle);
            graphics.setStroke(oldStroke);
            graphics.setColor(Color.BLACK);
            graphics.drawOval(x, y, EVENT_SIZE, EVENT_SIZE);

            x -= EVENT_SIZE + EVENT_SEP;
        }
    }

    public void drawPauseText(Graphics2D graphics) {
        TextLayout text = new TextLayout("PAUSED", MAIN_TEXT_FONT, graphics.getFontRenderContext());

        int width = (int) text.getBounds().getWidth();
        int height = (int) text.getBounds().getHeight();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(400 - width / 2 - 20, 300 - height / 2 - 20, width + 40, height + 40);
        graphics.setColor(Color.WHITE);
        text.draw(graphics, 400 - (float) width / 2 - 1, 300 + (float) height / 2 + 1);
    }

}
