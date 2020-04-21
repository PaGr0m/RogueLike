package ru.itmo.roguelike.handler;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class InputHandler implements KeyListener {

    public Map<Integer, Event> buttonSettings = new HashMap<>();
    public Map<Event, List<Runnable>> events = new HashMap<>();
    public Set<Event> activeButtons = new HashSet<>();

    {
        buttonSettings.put(KeyEvent.VK_UP, Event.MOVE_UP);
        buttonSettings.put(KeyEvent.VK_W, Event.MOVE_UP);

        buttonSettings.put(KeyEvent.VK_DOWN, Event.MOVE_DOWN);
        buttonSettings.put(KeyEvent.VK_S, Event.MOVE_DOWN);

        buttonSettings.put(KeyEvent.VK_LEFT, Event.MOVE_LEFT);
        buttonSettings.put(KeyEvent.VK_A, Event.MOVE_LEFT);

        buttonSettings.put(KeyEvent.VK_RIGHT, Event.MOVE_RIGHT);
        buttonSettings.put(KeyEvent.VK_D, Event.MOVE_RIGHT);
    }

    public InputHandler() {}

    /**
     * Клавиша нажата и отпущена
     *
     * @param keyEvent - содержит в себе всю информацию о нажатой клавише и о модификаторах
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    /**
     * Клавиша нажата, но не отпущена
     *
     * @param keyEvent - содержит в себе всю информацию о нажатой клавише и о модификаторах
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Event event = buttonSettings.get(keyEvent.getKeyCode());
        if (event != null) {
            activeButtons.add(event);
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
            activeButtons.remove(event);
        }
    }

    /**
     * Добавить выполняемое действие к событию
     *
     * @param event  - событие вызванное кнопкой
     * @param action - действие, которое необходимо выполнить
     */
    void registerEventListener(Event event, Runnable action) {
        if (events.containsKey(event)) {
            return;
        }

        List<Runnable> actions = events.getOrDefault(event, Collections.emptyList());
        actions.add(action);
        events.put(event, actions);
    }

    /**
     * Выполнить все действия активных кнопок
     */
    void handleInputs() {
        activeButtons.forEach(event -> events.get(event)
                                             .forEach(Runnable::run));

    }
}
