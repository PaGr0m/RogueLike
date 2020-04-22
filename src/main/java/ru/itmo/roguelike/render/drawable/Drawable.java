package ru.itmo.roguelike.render.drawable;

import java.util.ArrayList;
import java.util.List;

public abstract class Drawable {
    private final static List<Drawable> registry = new ArrayList<>();

    public Drawable() {
        registry.add(this);
    }

    public static List<Drawable> getRegistry() {
        return registry;
    }

    public abstract DrawableDescriptor draw();
}
