package ru.itmo.roguelike.input;


import javax.inject.Singleton;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

import static java.awt.event.KeyEvent.*;
import static ru.itmo.roguelike.input.Event.*;

@Singleton
public class InputHandler implements KeyListener {

    public static final Map<Integer, Event> buttonSettings = new HashMap<>();
    public static final Set<Event> singleEvents = new HashSet<>();

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

        buttonSettings.put(VK_P, PAUSE);

        buttonSettings.put(VK_ESCAPE, EXIT);

        singleEvents.addAll(Arrays.asList(
                EXIT, RESTART,
                USE_1, USE_2, USE_3, USE_4,
                USE_5, USE_6, USE_7, USE_8
        ));

    }

    public final Map<Event, List<Consumer<EventStatus>>> events = new EnumMap<>(Event.class);
    public final ConcurrentMap<Event, EventStatus> buttonStatus = new ConcurrentHashMap<>();

    {
        buttonStatus.put(MOVE_UP, new EventStatus());
        buttonStatus.put(MOVE_DOWN, new EventStatus());
        buttonStatus.put(MOVE_LEFT, new EventStatus());
        buttonStatus.put(MOVE_RIGHT, new EventStatus());
        buttonStatus.put(FIRE_UP, new EventStatus());
        buttonStatus.put(FIRE_DOWN, new EventStatus());
        buttonStatus.put(FIRE_LEFT, new EventStatus());
        buttonStatus.put(FIRE_RIGHT, new EventStatus());
        buttonStatus.put(RESTART, new EventStatus());

        buttonStatus.put(USE_1, new EventStatus());
        buttonStatus.put(USE_2, new EventStatus());
        buttonStatus.put(USE_3, new EventStatus());
        buttonStatus.put(USE_4, new EventStatus());
        buttonStatus.put(USE_5, new EventStatus());
        buttonStatus.put(USE_6, new EventStatus());
        buttonStatus.put(USE_7, new EventStatus());
        buttonStatus.put(USE_8, new EventStatus());

        buttonStatus.put(EXIT, new EventStatus());
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
        synchronized (buttonStatus) {
            Event event = buttonSettings.get(keyEvent.getKeyCode());
            if (event != null) {
                buttonStatus.get(event).addFlags(keyEvent);
            }
        }
    }

    /**
     * Клавиша отпущена
     *
     * @param keyEvent - содержит в себе всю информацию о нажатой клавише и о модификаторах
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        synchronized (buttonStatus) {
            Event event = buttonSettings.get(keyEvent.getKeyCode());
            if (event != null) {
                if (singleEvents.contains(event)) {
                    for (Consumer<EventStatus> runnable : events.get(event)) {
                        runnable.accept(buttonStatus.get(event));
                    }
                }
                buttonStatus.get(event).clear();
            }
        }
    }

    /**
     * Добавить выполняемое действие к событию
     *
     * @param event  - событие вызванное кнопкой
     * @param action - действие, которое необходимо выполнить
     */
    public void registerEventListener(Event event, Consumer<EventStatus> action) {
        if (events.containsKey(event)) {
            return;
        }
        List<Consumer<EventStatus>> actions = events.getOrDefault(event, new ArrayList<>());
        actions.add(action);
        events.put(event, actions);
    }

    /**
     * Выполнить все действия активных кнопок
     */
    public void handleInputs() {
        synchronized (buttonStatus) {
            for (Map.Entry<Event, EventStatus> button : buttonStatus.entrySet()) {
                if (!button.getValue().isNone()) {
                    if (!singleEvents.contains(button.getKey())) {
                        for (Consumer<EventStatus> runnable : events.get(button.getKey())) {
                            if (runnable != null) {
                                runnable.accept(button.getValue());
                            }
                        }
                    }
                }
            }
        }
    }

}
