package ru.itmo.roguelike.manager.actormanager;

import ru.itmo.roguelike.characters.mobs.Enemy;

import java.util.ArrayList;
import java.util.List;

/**
 * Register and perform act on all mobs
 */
public class MobManager implements ActorManager {
    /**
     * List of all available mobs
     */
    private final static List<Enemy> registry = new ArrayList<>();

    public static void addToRegister(Enemy enemy) {
        registry.add(enemy);
    }

    public static void deleteFromRegister(Enemy enemy) {
        registry.remove(enemy);
    }

    public static List<Enemy> getRegistry() {
        return registry;
    }

    /**
     * Made all mobs do step
     */
    public void actAll() {
        registry.forEach(Enemy::go);
    }
}
