package ru.itmo.roguelike.manager.collidemanager;

import ru.itmo.roguelike.Collidable;

import java.util.HashSet;

public class CollideManager {
    private final static int GAP = 3;

    private static final HashSet<Collidable> collidables = new HashSet<>();
    private static final HashSet<Collidable> toRemove = new HashSet<>();

    public static void register(Collidable c) {
        collidables.add(c);
    }

    public static void unregister(Collidable c) {
        toRemove.add(c);
    }

    public static void collideAll() {
        collidables.removeAll(toRemove);
        toRemove.clear();

        for (Collidable a : collidables) {
            for (Collidable b : collidables) {
                if (a != b && intersects(a, b)) {
                    a.collide(b);
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
