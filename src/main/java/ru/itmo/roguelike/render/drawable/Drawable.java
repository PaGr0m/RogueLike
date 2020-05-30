package ru.itmo.roguelike.render.drawable;

import ru.itmo.roguelike.render.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes all objects drawn on the screen
 */
public abstract class Drawable {
    /**
     * All available drawable objects on the background
     */
    private final static List<Drawable> registry = new ArrayList<>();

    /**
     * All available drawable objects on the foreground
     */
    private final static List<Drawable> foregroundRegistry = new ArrayList<>();

    protected final DrawableDescriptor drawableDescriptor = new DrawableDescriptor();
    private final Drawer drawer;

    public Drawable() {
        this(false);
    }

    public Drawable(Drawer drawer, boolean background) {
        this.drawer = drawer;

        if (background) {
            registry.add(this);
        } else {
            foregroundRegistry.add(this);
        }
    }

    public Drawable(boolean background) {
        this((g, x, y) -> g.fillRect(x, y, 10, 10), background);
    }

    public Drawable(Drawer drawer) {
        this(drawer, false);
    }

    public static List<Drawable> getBackgroundRegistry() {
        return registry;
    }

    public static List<Drawable> getRegistry() {
        return foregroundRegistry;
    }

    public static void unregisterBackground(Drawable drawable) {
        registry.remove(drawable);
    }

    public static void unregister(Drawable drawable) {
        foregroundRegistry.remove(drawable);
    }

    public void draw(Graphics2D graphics, Camera camera) {
        graphics.setColor(drawableDescriptor.getColor());
        camera.transformAndGet(drawableDescriptor.getPosition())
                .ifPresent(p -> drawer.draw(graphics, p.getX(), p.getY()));
    }

    public DrawableDescriptor getDrawableDescriptor() {
        return drawableDescriptor;
    }

    public interface Drawer {
        void draw(Graphics2D graphics, int x, int y);
    }
}
