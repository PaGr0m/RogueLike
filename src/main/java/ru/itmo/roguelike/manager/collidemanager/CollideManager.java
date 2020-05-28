package ru.itmo.roguelike.manager.collidemanager;

import ru.itmo.roguelike.Collidable;

import java.awt.*;
import java.util.HashSet;

public class CollideManager {
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


    /**
     * Checks a's and b's shapes intersection
     */
    private static boolean intersects(Collidable a, Collidable b) {
        Shape shapeA = a.getShapeAtPosition();
        Shape shapeB = b.getShapeAtPosition();
        return shapeA.intersects(shapeB.getBounds());
    }
}
