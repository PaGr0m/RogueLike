package ru.itmo.roguelike.manager.eventmanager;

import ru.itmo.roguelike.render.drawable.Drawable;

import java.awt.*;
import java.util.function.IntConsumer;

public class Event {
    private final int MAX;
    private final Color color;
    private final IntConsumer runner;
    private final Drawable.Drawer drawer;
    private int curr;


    public Event(int MAX, int curr, Color color, IntConsumer runner, Drawable.Drawer drawer) {
        this.MAX = MAX;
        this.curr = curr;
        this.color = color;
        this.runner = runner;
        this.drawer = drawer;
    }

    public Event(int MAX, int curr, Color color, IntConsumer runner) {
        this(MAX, curr, color, runner, null);
    }

    public Event(int max, int curr, Color color) {
        this(max, curr, color, null);
    }

    public Event(int max, int curr) {
        this(max, curr, Color.LIGHT_GRAY);
    }


    public int getDuration() {
        return MAX;
    }

    public Color getColor() {
        return color;
    }

    public int getCurr() {
        return curr;
    }

    public void setCurr(int curr) {
        this.curr = curr;
    }

    public void run() {
        if (runner != null) {
            runner.accept(curr);
        }
    }

    public void draw(Graphics2D graphics, int x, int y) {
        if (drawer != null) {
            drawer.draw(graphics, x, y);
        }
    }
}
