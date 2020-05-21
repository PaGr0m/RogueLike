package ru.itmo.roguelike.manager.collidemanager;

import ru.itmo.roguelike.Collidable;

import java.util.HashSet;

public class CollideManager {
    private final static int GAP = 3;

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
        return a.getX() <= b.getX() + b.getWidth() - GAP &&
                a.getX() + a.getWidth() >= b.getX() + GAP &&
                a.getY() <= b.getY() + b.getHeight() - GAP &&
                a.getY() + a.getHeight() >= b.getY() + GAP;
    }
}
