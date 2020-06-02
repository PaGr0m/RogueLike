package ru.itmo.roguelike.manager.eventmanager;

import ru.itmo.roguelike.utils.FuncUtils.BoolFunc;

import javax.inject.Singleton;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Singleton
public class EventManager {
    private final Set<BoolFunc> eventFunctions = new HashSet<>();
    private final Set<BoolFunc> toRemove = new HashSet<>();

    private final List<Event> drawableEvents = new ArrayList<>();

    public void addDrawableEvent(Event event) {
        drawableEvents.add(event);
    }

    public void removeDrawableEvent(Event event) {
        drawableEvents.remove(event);
    }

    public void add(BoolFunc event) {
        eventFunctions.add(event);
    }

    public void actAll() {
        for (BoolFunc e : eventFunctions) {
            if (!e.get()) {
                toRemove.add(e);
            }
        }
        eventFunctions.removeAll(toRemove);
        toRemove.clear();
    }

    public void clear() {
        eventFunctions.clear();
        toRemove.clear();
        drawableEvents.clear();
    }

    public List<Event> getDrawableEvents() {
        return drawableEvents;
    }
}
