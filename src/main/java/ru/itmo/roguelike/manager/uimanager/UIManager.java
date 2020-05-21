package ru.itmo.roguelike.manager.uimanager;

import java.awt.*;

public class UIManager {

    public UIManager() {}

    public static void addStatusBar(Graphics graphics) {
        // FIXME: magic numbers
        int height = 30;
        int width = 10;
        int delta = 20;

        graphics.setFont(new Font("Courier New", Font.BOLD, 24));
        graphics.setColor(Color.ORANGE);
        graphics.drawString("Status", width, height);

        graphics.setFont(new Font("Courier New", Font.BOLD, 12));
        graphics.drawString("HP: ", width, height += delta);
        graphics.drawString("MP: ", width, height += delta);
    }
}
