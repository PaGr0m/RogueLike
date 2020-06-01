package ru.itmo.roguelike.manager.actormanager;

import ru.itmo.roguelike.field.Field;

public interface ActorManager {
    void actAll(Field field);

    void killAll();
}
