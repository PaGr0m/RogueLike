package ru.itmo.roguelike.render.particles;

import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public abstract class Particle extends Drawable {
    private static Set<Particle> particles = new HashSet<>();

    protected int time = 0;

    public Particle(IntCoordinate position) {
        drawableDescriptor.setPosition(position);
        particles.add(this);
    }

    public abstract int getTTL();

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        super.draw(graphics, camera);
        time++;
    }

    public static void deleteOld() {
        HashSet<Particle> toRemove = new HashSet<>();

        for (Particle particle : particles) {
            if (particle.time > particle.getTTL()) {
                unregister(particle);
                toRemove.add(particle);
            }
        }

        particles.removeAll(toRemove);
    }
}
