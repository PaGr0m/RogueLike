package ru.itmo.roguelike.manager.uimanager;

import java.awt.*;

public class UIManager {

    public UIManager() {}

    public static void addStatusBar(Graphics graphics) {
        int width = 10;
        int height = 30;
        graphics.setFont(new Font("Courier New", Font.BOLD, 24));
        graphics.setColor(Color.ORANGE);
        graphics.drawString("Status", width, 30);

        graphics.setFont(new Font("Courier New", Font.BOLD, 12));
        graphics.drawString("HP: ", width , height+= 20);
        graphics.drawString("MP: ", width , height+= 20);
    }
}
