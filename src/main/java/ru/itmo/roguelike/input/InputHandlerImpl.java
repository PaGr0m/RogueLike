package ru.itmo.roguelike.input;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Handle pressed keys
 */
public class InputHandlerImpl implements KeyListener, InputHandler {

    public Map<Event, List<Runnable>> events = new EnumMap<>(Event.class);
    public ConcurrentMap<Event, Boolean> buttonStatus = new ConcurrentHashMap<>();
    public static Map<Integer, Event> buttonSettings = new HashMap<>();

    {
        buttonStatus.put(Event.MOVE_UP, false);
        buttonStatus.put(Event.MOVE_DOWN, false);
        buttonStatus.put(Event.MOVE_LEFT, false);
        buttonStatus.put(Event.MOVE_RIGHT, false);
    }

    static {
        buttonSettings.put(KeyEvent.VK_UP, Event.MOVE_UP);
        buttonSettings.put(KeyEvent.VK_W, Event.MOVE_UP);

        buttonSettings.put(KeyEvent.VK_DOWN, Event.MOVE_DOWN);
        buttonSettings.put(KeyEvent.VK_S, Event.MOVE_DOWN);

        buttonSettings.put(KeyEvent.VK_LEFT, Event.MOVE_LEFT);
        buttonSettings.put(KeyEvent.VK_A, Event.MOVE_LEFT);

        buttonSettings.put(KeyEvent.VK_RIGHT, Event.MOVE_RIGHT);
        buttonSettings.put(KeyEvent.VK_D, Event.MOVE_RIGHT);
    }

    public InputHandlerImpl() {
    }

    /**
     * Key was pushed and released
     *
     * @param keyEvent - contains all the information about the pressed key and the modifiers
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
     * Key was pushed but wasn't released
     *
     * @param keyEvent - contains all the information about the pressed key and the modifiers
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Event event = buttonSettings.get(keyEvent.getKeyCode());
        if (event != null) {
            buttonStatus.put(event, true);
        }
    }

    /**
     * Key was released
     *
     * @param keyEvent - contains all the information about the pressed key and the modifiers
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        Event event = buttonSettings.get(keyEvent.getKeyCode());
        if (event != null) {
            buttonStatus.put(event, false);
        }
    }

    /**
     * Добавить выполняемое действие к событию
     *
     * @param event  - событие вызванное кнопкой
     * @param action - действие, которое необходимо выполнить
     */
    @Override
    public void registerEventListener(Event event, Runnable action) {
        if (events.containsKey(event)) {
            return;
        }

        List<Runnable> actions = events.get(event);

        if (actions == null) {
            actions = new ArrayList<>();
        }

        actions.add(action);
        events.put(event, actions);
    }

    /**
     * Perform all events on active keys
     */
    @Override
    public void handleInputs() {
        for (Map.Entry<Event, Boolean> button : buttonStatus.entrySet()) {
            if (button.getValue()) {
                for (Runnable runnable : events.get(button.getKey())) {
                    if (runnable != null) runnable.run();
                }
            }
        }
    }
}
