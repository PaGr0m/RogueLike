package ru.itmo.roguelike.items;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.inventory.Usable;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;

public abstract class Collectible extends Drawable implements Collidable, Usable {
    protected int width;
    protected int height;
    protected Color color;
    protected BonusType bonus;
    protected int bonusSize;
    private IntCoordinate position = IntCoordinate.getZeroPosition();

    {
        CollideManager.register(this);
    }

    public Collectible() {
        drawableDescriptor.setPosition(position);
        drawableDescriptor.setColor(getColor());
    }

    public Collectible(Drawer drawer) {
        super(drawer);
        drawableDescriptor.setPosition(position);
        drawableDescriptor.setColor(getColor());
    }

    protected boolean used = false;

    @Override
    public boolean isUsed() {
        return used;
    }

    @Override
    public void collide(Collidable c) {

    }

    /**
     * Collectible is picked up by some actor. It will stop rendering, occupying a tile and checking collisions, but
     * still might be available for use (if does not have permanent usage effect).
     */
    public void pickUp() {
        new MovingUpText(position, "+ " + this.getClass().getSimpleName(), Color.MAGENTA);
        CollideManager.unregister(this);
        Drawable.unregister(this);
    }

    @Override
    public IntCoordinate getPosition() {
        return position;
    }

    @Override
    public void setPosition(IntCoordinate coordinate) {
        position = new IntCoordinate(coordinate);
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        drawableDescriptor.setPosition(position);
        super.draw(graphics, camera);
    }
}
