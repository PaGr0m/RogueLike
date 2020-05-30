package ru.itmo.roguelike.input;


import javax.inject.Singleton;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Singleton
public class InputHandler implements KeyListener {

    public static Map<Integer, Event> buttonSettings = new HashMap<>();

    static {
        buttonSettings.put(KeyEvent.VK_UP, Event.FIRE_UP);
        buttonSettings.put(KeyEvent.VK_W, Event.MOVE_UP);

        buttonSettings.put(KeyEvent.VK_DOWN, Event.FIRE_DOWN);
        buttonSettings.put(KeyEvent.VK_S, Event.MOVE_DOWN);

        buttonSettings.put(KeyEvent.VK_LEFT, Event.FIRE_LEFT);
        buttonSettings.put(KeyEvent.VK_A, Event.MOVE_LEFT);

        buttonSettings.put(KeyEvent.VK_RIGHT, Event.FIRE_RIGHT);
        buttonSettings.put(KeyEvent.VK_D, Event.MOVE_RIGHT);

        buttonSettings.put(KeyEvent.VK_R, Event.RESTART);

        buttonSettings.put(KeyEvent.VK_1, Event.USE_1);
        buttonSettings.put(KeyEvent.VK_2, Event.USE_2);
        buttonSettings.put(KeyEvent.VK_3, Event.USE_3);
        buttonSettings.put(KeyEvent.VK_4, Event.USE_4);
        buttonSettings.put(KeyEvent.VK_5, Event.USE_5);
        buttonSettings.put(KeyEvent.VK_6, Event.USE_6);
        buttonSettings.put(KeyEvent.VK_7, Event.USE_7);
        buttonSettings.put(KeyEvent.VK_8, Event.USE_8);
    }

    public Map<Event, List<Runnable>> events = new EnumMap<>(Event.class);
    public ConcurrentMap<Event, Boolean> buttonStatus = new ConcurrentHashMap<>();

    {
        buttonStatus.put(Event.MOVE_UP, false);
        buttonStatus.put(Event.MOVE_DOWN, false);
        buttonStatus.put(Event.MOVE_LEFT, false);
        buttonStatus.put(Event.MOVE_RIGHT, false);
        buttonStatus.put(Event.FIRE_UP, false);
        buttonStatus.put(Event.FIRE_DOWN, false);
        buttonStatus.put(Event.FIRE_LEFT, false);
        buttonStatus.put(Event.FIRE_RIGHT, false);
        buttonStatus.put(Event.RESTART, false);

        buttonStatus.put(Event.USE_1, false);
        buttonStatus.put(Event.USE_2, false);
        buttonStatus.put(Event.USE_3, false);
        buttonStatus.put(Event.USE_4, false);
        buttonStatus.put(Event.USE_5, false);
        buttonStatus.put(Event.USE_6, false);
        buttonStatus.put(Event.USE_7, false);
        buttonStatus.put(Event.USE_8, false);
    }

    public InputHandler() {
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
