package ru.itmo.roguelike.input;

import javax.lang.model.element.Modifier;
import java.awt.event.KeyEvent;

public class EventStatus {
    public static final int NONE = 0b0;
    public static final int PRESSED = 0b1;
    public static final int PRESSED_SHIFT = 0b10;
    public static final int PRESSED_ALT = 0b100;
    public static final int PRESSED_CTRL = 0b100;

    private int flags = NONE;

    public void addFlag(int flag) {
        flags |= flag;
    }

    public boolean flagIsSet(int flag) {
        return (flags & flag) != NONE;
    }

    public boolean isNone() {
        return flags == NONE;
    }

    public void clear() {
        flags = NONE;
    }

    public void addFlags(KeyEvent event) {
        addFlag(PRESSED);
        if (event.isShiftDown()) addFlag(PRESSED_SHIFT);
        if (event.isAltDown() || event.isAltGraphDown()) addFlag(PRESSED_ALT);
        if (event.isControlDown()) addFlag(PRESSED_CTRL);
    }

}
