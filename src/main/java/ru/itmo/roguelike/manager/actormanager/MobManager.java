package ru.itmo.roguelike.manager.actormanager;

import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.render.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class MobManager implements ActorManager {
    /**
     * List of all mobs
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

    public void actAll() {

    }
}
