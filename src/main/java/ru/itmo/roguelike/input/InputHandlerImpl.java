package ru.itmo.roguelike.input;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class InputHandlerImpl implements KeyListener, InputHandler {

    public Map<Event, List<Runnable>> events = new EnumMap<>(Event.class);
    public Set<Event> activeButtons = new HashSet<>();

    public static Map<Integer, Event> buttonSettings = new HashMap<>();

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
        activeButtons.forEach(event -> events.get(event)
                                             .stream()
                                             .filter(Objects::nonNull)
                                             .forEach(Runnable::run));
    }
}
