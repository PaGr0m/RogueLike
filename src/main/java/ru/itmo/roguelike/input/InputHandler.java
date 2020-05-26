package ru.itmo.roguelike.input;

/**
 * Handle input signals (by mouse/keyboard/etc)
 */
public interface InputHandler {
    void registerEventListener(Event event, Runnable action);

    void handleInputs();
}
