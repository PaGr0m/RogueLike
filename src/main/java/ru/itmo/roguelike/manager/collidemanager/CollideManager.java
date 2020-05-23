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
        return a.getCoordinate().getX() <= b.getCoordinate().getX() + b.getWidth() - GAP &&
               a.getCoordinate().getX() + a.getWidth() >= b.getCoordinate().getX() + GAP &&
               a.getCoordinate().getY() <= b.getCoordinate().getY() + b.getHeight() - GAP &&
               a.getCoordinate().getY() + a.getHeight() >= b.getCoordinate().getY() + GAP;
    }
}
