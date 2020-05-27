package ru.itmo.roguelike.manager.collidemanager;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.utils.IntCoordinate;

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
        IntCoordinate positionA = a.getPosition();
        IntCoordinate positionB = b.getPosition();
        return positionA.getX() <= positionB.getX() + b.getWidth() - GAP &&
                positionA.getX() + a.getWidth() >= positionB.getX() + GAP &&
                positionA.getY() <= positionB.getY() + b.getHeight() - GAP &&
                positionA.getY() + a.getHeight() >= positionB.getY() + GAP;
    }
}
