package ru.itmo.roguelike.render.drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Class describes everything that should be drew
 */
public abstract class Drawable {
    /**
     * All avaliable drawable objects
     */
    private final static List<Drawable> registry = new ArrayList<>();

    public Drawable() {
        registry.add(this);
    }

    public static List<Drawable> getRegistry() {
        return registry;
    }

    public static void unregister(Drawable drawable) {
        registry.remove(drawable);
    }

    protected final DrawableDescriptor drawableDescriptor = new DrawableDescriptor();

    /**
     * Draw object on screen
     */
    public abstract void draw();

    public DrawableDescriptor getDrawableDescriptor() {
        return drawableDescriptor;
    }
}
