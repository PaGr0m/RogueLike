package ru.itmo.roguelike.render.drawable;

import java.util.ArrayList;
import java.util.List;

public abstract class Drawable {
    private final static List<Drawable> registry = new ArrayList<>();
    protected final DrawableDescriptor drawableDescriptor = new DrawableDescriptor();

    public Drawable() {
        registry.add(this);
    }

    public static List<Drawable> getRegistry() {
        return registry;
    }

    public static void unregister(Drawable drawable) {
        registry.remove(drawable);
    }

    public abstract void draw();

    public DrawableDescriptor getDrawableDescriptor() {
        return drawableDescriptor;
    }
}
