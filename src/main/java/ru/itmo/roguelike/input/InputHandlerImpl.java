package ru.itmo.roguelike.input;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InputHandlerImpl implements KeyListener, InputHandler {

    public Map<Event, List<Runnable>> events = new EnumMap<>(Event.class);
    public ConcurrentMap<Event, Boolean> buttonStatus = new ConcurrentHashMap<>();
    public static Map<Integer, Event> buttonSettings = new HashMap<>();

    {
        buttonStatus.put(Event.MOVE_UP, false);
        buttonStatus.put(Event.MOVE_DOWN, false);
        buttonStatus.put(Event.MOVE_LEFT, false);
        buttonStatus.put(Event.MOVE_RIGHT, false);
        buttonStatus.put(Event.FIRE_UP, false);
        buttonStatus.put(Event.FIRE_DOWN, false);
        buttonStatus.put(Event.FIRE_LEFT, false);
        buttonStatus.put(Event.FIRE_RIGHT, false);
    }

    static {
        buttonSettings.put(KeyEvent.VK_UP, Event.FIRE_UP);
        buttonSettings.put(KeyEvent.VK_W, Event.MOVE_UP);

        buttonSettings.put(KeyEvent.VK_DOWN, Event.FIRE_DOWN);
        buttonSettings.put(KeyEvent.VK_S, Event.MOVE_DOWN);

        buttonSettings.put(KeyEvent.VK_LEFT, Event.FIRE_LEFT);
        buttonSettings.put(KeyEvent.VK_A, Event.MOVE_LEFT);

        buttonSettings.put(KeyEvent.VK_RIGHT, Event.FIRE_RIGHT);
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
            buttonStatus.put(event, true);
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
     * Выполнить все действия активных кнопок
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
