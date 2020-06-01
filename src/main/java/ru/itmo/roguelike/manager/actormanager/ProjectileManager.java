package ru.itmo.roguelike.manager.actormanager;

import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.characters.projectiles.Projectile;
import ru.itmo.roguelike.field.Field;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
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

    public void killAll() {
        registry.forEach(Projectile::die);
        registry.clear();
        toDelete.clear();
    }
}
