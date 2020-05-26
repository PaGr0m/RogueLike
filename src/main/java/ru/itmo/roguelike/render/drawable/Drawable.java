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
//        Color col = new Color(255 - drawableDescriptor.getColor().getRed(),
//                255 - drawableDescriptor.getColor().getGreen(),
//                255 - drawableDescriptor.getColor().getBlue());
//        graphics.setColor(col);
        graphics.setColor(drawableDescriptor.getColor());
        camera.transformAndGet(drawableDescriptor.getX(), drawableDescriptor.getY())
                .ifPresent(p -> drawer.draw(graphics, p.getFirst(), p.getSecond()));
    }

    public DrawableDescriptor getDrawableDescriptor() {
        return drawableDescriptor;
    }

    public interface Drawer {
        void draw(Graphics2D graphics, int x, int y);
    }
}
