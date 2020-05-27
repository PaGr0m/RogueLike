package ru.itmo.roguelike.render.drawable;

import ru.itmo.roguelike.render.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Drawable {
    private final static List<Drawable> registry = new ArrayList<>();
    protected final DrawableDescriptor drawableDescriptor = new DrawableDescriptor();
    private final Drawer drawer;

    public Drawable() {
        this((g, x, y) -> g.fillRect(x, y, 10, 10));
    }

    public Drawable(Drawer drawer) {
        this.drawer = drawer;
        registry.add(this);
    }

    public static List<Drawable> getRegistry() {
        return registry;
    }

    public static void unregister(Drawable drawable) {
        registry.remove(drawable);
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
