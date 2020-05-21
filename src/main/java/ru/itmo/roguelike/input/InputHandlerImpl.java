package ru.itmo.roguelike.input;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class InputHandlerImpl implements KeyListener, InputHandler {

    public Map<Integer, Event> buttonSettings = new HashMap<>();
    public Map<Event, List<Runnable>> events = new EnumMap<>(Event.class);
    public ConcurrentMap<Event, Boolean> activeButtons = new ConcurrentHashMap<>();


    {
        activeButtons.put(Event.MOVE_UP, false);
        activeButtons.put(Event.MOVE_DOWN, false);
        activeButtons.put(Event.MOVE_LEFT, false);
        activeButtons.put(Event.MOVE_RIGHT, false);

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
     * Клавиша нажата и отпущена
     *
     * @param keyEvent - содержит в себе всю информацию о нажатой клавише и о модификаторах
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
     * Клавиша нажата, но не отпущена
     *
     * @param keyEvent - содержит в себе всю информацию о нажатой клавише и о модификаторах
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Event event = buttonSettings.get(keyEvent.getKeyCode());
        if (event != null) {
            activeButtons.put(event, true);
        }
    }

    /**
     * Клавиша отпущена
     *
     * @param keyEvent - содержит в себе всю информацию о нажатой клавише и о модификаторах
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        Event event = buttonSettings.get(keyEvent.getKeyCode());
        if (event != null) {
            activeButtons.put(event, false);
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
     * Выполнить все действия активных кнопок
     */
    @Override
    public void handleInputs() {
        for (Map.Entry<Event, Boolean> button : activeButtons.entrySet()) {
            if (button.getValue()) {
                for (Runnable runnable : events.get(button.getKey())) {
                    if (runnable != null) runnable.run();
                }
            }
        }
    }
}
