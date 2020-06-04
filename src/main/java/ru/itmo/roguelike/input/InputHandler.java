package ru.itmo.roguelike.input;

public interface InputHandler {
    void registerEventListener(Event event, Runnable action);

    void handleInputs();
}
