package ru.itmo.roguelike.input;


import javax.inject.Singleton;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.awt.event.KeyEvent.*;
import static ru.itmo.roguelike.input.Event.*;

@Singleton
public class InputHandler implements KeyListener {

    public static Map<Integer, Event> buttonSettings = new HashMap<>();
    public static Set<Event> singleEvents = new HashSet<>();

    static {
        buttonSettings.put(VK_UP, FIRE_UP);
        buttonSettings.put(VK_W, MOVE_UP);

        buttonSettings.put(VK_DOWN, FIRE_DOWN);
        buttonSettings.put(VK_S, MOVE_DOWN);

        buttonSettings.put(VK_LEFT, FIRE_LEFT);
        buttonSettings.put(VK_A, MOVE_LEFT);

        buttonSettings.put(VK_RIGHT, FIRE_RIGHT);
        buttonSettings.put(VK_D, MOVE_RIGHT);

        buttonSettings.put(VK_R, RESTART);

        buttonSettings.put(VK_1, USE_1);
        buttonSettings.put(VK_2, USE_2);
        buttonSettings.put(VK_3, USE_3);
        buttonSettings.put(VK_4, USE_4);
        buttonSettings.put(VK_5, USE_5);
        buttonSettings.put(VK_6, USE_6);
        buttonSettings.put(VK_7, USE_7);
        buttonSettings.put(VK_8, USE_8);

        buttonSettings.put(VK_ESCAPE, EXIT);

        singleEvents.addAll(Arrays.asList(EXIT, USE_1, USE_2, USE_3, USE_4, USE_5, USE_6, USE_7, USE_8, RESTART));
    }

    public Map<Event, List<Runnable>> events = new EnumMap<>(Event.class);
    public ConcurrentMap<Event, Boolean> buttonStatus = new ConcurrentHashMap<>();

    {
        buttonStatus.put(MOVE_UP, false);
        buttonStatus.put(MOVE_DOWN, false);
        buttonStatus.put(MOVE_LEFT, false);
        buttonStatus.put(MOVE_RIGHT, false);
        buttonStatus.put(FIRE_UP, false);
        buttonStatus.put(FIRE_DOWN, false);
        buttonStatus.put(FIRE_LEFT, false);
        buttonStatus.put(FIRE_RIGHT, false);
        buttonStatus.put(RESTART, false);

        buttonStatus.put(USE_1, false);
        buttonStatus.put(USE_2, false);
        buttonStatus.put(USE_3, false);
        buttonStatus.put(USE_4, false);
        buttonStatus.put(USE_5, false);
        buttonStatus.put(USE_6, false);
        buttonStatus.put(USE_7, false);
        buttonStatus.put(USE_8, false);

        buttonStatus.put(EXIT, false);
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
            if (singleEvents.contains(event)) {
                for (Runnable runnable : events.get(event)) {
                    runnable.run();
                }
            }
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
                if (!singleEvents.contains(button.getKey())) {
                    for (Runnable runnable : events.get(button.getKey())) {
                        if (runnable != null) runnable.run();
                    }
                }
            }
        }
    }
}
