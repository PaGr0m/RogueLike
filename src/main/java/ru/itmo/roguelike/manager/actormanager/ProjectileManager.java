package ru.itmo.roguelike.manager.actormanager;

import ru.itmo.roguelike.characters.projectiles.Projectile;

import java.util.ArrayList;
import java.util.List;

/**
 * Register and perform act on all projectiles
 */
public class ProjectileManager implements ActorManager {
    /**
     * List of all available projectiles
     */
    private final static List<Projectile> registry = new ArrayList<>();

    public static void addToRegister(Projectile projectile) {
        registry.add(projectile);
    }

    public static void deleteFromRegister(Projectile projectile) {
        registry.remove(projectile);
    }

    public static List<Projectile> getRegistry() {
        return registry;
    }

    /**
     * Made all projectiles do step
     */
    public void actAll() {
        registry.forEach(Projectile::go);
    }
}
