package ru.itmo.roguelike.manager.actormanager;

import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.field.Field;

import java.util.HashSet;
import java.util.Set;

public class MobManager implements ActorManager {
    /**
     * List of all mobs
     */
    private final static Set<Enemy> registry = new HashSet<>();
    private final static Set<Enemy> toRemove = new HashSet<>();

    public static void addToRegister(Enemy enemy) {
        registry.add(enemy);
    }

    public static void deleteFromRegister(Enemy enemy) {
        toRemove.add(enemy);
    }

    public static Set<Enemy> getRegistry() {
        return registry;
    }

    public void actAll(Field field) {
        registry.forEach(e -> e.go(field));
        registry.removeAll(toRemove);
    }
}
