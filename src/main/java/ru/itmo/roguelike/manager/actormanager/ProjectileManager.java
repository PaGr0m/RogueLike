package ru.itmo.roguelike.manager.actormanager;

import ru.itmo.roguelike.characters.projectiles.Projectile;
import ru.itmo.roguelike.field.Field;

import java.util.ArrayList;
import java.util.List;

public class ProjectileManager implements ActorManager {
    /**
     * List of all mobs
     */
    private final static List<Projectile> registry = new ArrayList<>();
    private final static List<Projectile> toDelete = new ArrayList<>();

    public static void addToRegister(Projectile projectile) {
        registry.add(projectile);
    }

    public static void deleteFromRegister(Projectile projectile) {
        toDelete.add(projectile);
    }

    public static List<Projectile> getRegistry() {
        return registry;
    }

    public void actAll(Field field) {
        registry.forEach(reg -> reg.act(field));
        registry.removeAll(toDelete);
    }
}
