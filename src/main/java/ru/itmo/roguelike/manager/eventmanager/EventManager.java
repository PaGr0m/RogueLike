package ru.itmo.roguelike.manager.eventmanager;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BooleanSupplier;

@Singleton
public class EventManager {
    private final Set<BooleanSupplier> eventFunctions = new HashSet<>();
    private final Set<BooleanSupplier> toRemove = new HashSet<>();

    private final List<Event> drawableEvents = new ArrayList<>();

    public void addDrawableEvent(Event event) {
        drawableEvents.add(event);
    }

    public void removeDrawableEvent(Event event) {
        drawableEvents.remove(event);
    }

    public void add(BooleanSupplier event) {
        eventFunctions.add(event);
    }

    public void actAll() {
        for (BooleanSupplier e : eventFunctions) {
            if (!e.getAsBoolean()) {
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
