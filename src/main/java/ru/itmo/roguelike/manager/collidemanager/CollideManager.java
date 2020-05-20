package ru.itmo.roguelike.manager.collidemanager;

import ru.itmo.roguelike.Collidable;

import java.util.HashMap;
import java.util.HashSet;

public class CollideManager {
    private final HashSet<Collidable> collidables = new HashSet<>();
    private final HashSet<Collidable> staticCollidables = new HashSet<>();

    public void register(Collidable c) {
        collidables.add(c);
    }

    public void registerStatic(Collidable c) {
        staticCollidables.add(c);
    }

    public void unregister(Collidable c) {
        collidables.remove(c);
    }

    public void collideAll() {
        for (Collidable a : collidables) {
            for (Collidable b : collidables) {
                if (a != b && intersects(a, b)) {
                    a.collide(b);
                }
            }
        }

        for (Collidable s : staticCollidables) {
            for (Collidable a : collidables) {
                if (intersects(a, s)) {
                    a.collide(s);
                }
            }
        }
    }

    private static boolean intersects(Collidable a, Collidable b) {
        if (a.getX() > b.getX() + b.getWidth()) return false;
        if (a.getX() + a.getWidth() < b.getX()) return false;
        if (a.getY() > b.getY() + b.getHeight()) return false;
        if (a.getY() + a.getHeight() < b.getY()) return false;
        return true;
    }
}
