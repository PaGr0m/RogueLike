package ru.itmo.roguelike.render.particles;

import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Small short-lived objects that do not have a physical body.
 */
public abstract class Particle extends Drawable {
    /**
     * Set of all particles
     */
    private static final Set<Particle> particles = new HashSet<>();

    /**
     * current life time
     */
    protected int time = 0;

    /**
     * @param position particle spawn position
     */
    public Particle(IntCoordinate position) {
        drawableDescriptor.setPosition(position);
        particles.add(this);
    }

    /**
     * Removes dead particles
     */
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

    /**
     * @return maximum life time
     */
    public abstract int getTTL();

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        super.draw(graphics, camera);
        time++;
    }
}
