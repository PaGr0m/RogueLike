package ru.itmo.roguelike.manager.eventmanager;

import java.awt.*;
import java.util.function.IntConsumer;

public class Event {
    private final int MAX;
    private final Color color;
    private final IntConsumer runner;
    private int curr;

    public Event(int MAX, int curr, Color color, IntConsumer runner) {
        this.MAX = MAX;
        this.curr = curr;
        this.color = color;
        this.runner = runner;
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
        runner.accept(curr);
    }
}
