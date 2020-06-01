package ru.itmo.roguelike.manager.eventmanager;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.input.Event;
import ru.itmo.roguelike.utils.FuncUtils;
import ru.itmo.roguelike.utils.FuncUtils.BoolFunc;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class EventManager {
    private final Set<BoolFunc> events = new HashSet<>();
    private final Set<BoolFunc> toRemove = new HashSet<>();

    public void add(BoolFunc event) {
        events.add(event);
    }

    public void actAll() {
        for (BoolFunc e : events) {
            if (!e.get()) {
                toRemove.add(e);
            }
        }
        events.removeAll(toRemove);
        toRemove.clear();
    }

    public void clear() {
        events.clear();
        toRemove.clear();
    }
}
