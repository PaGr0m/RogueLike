package ru.itmo.roguelike.items;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.inventory.Droppable;
import ru.itmo.roguelike.characters.inventory.Usable;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.manager.uimanager.UIManager;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.render.particles.Blinking;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;
import java.awt.font.TextLayout;

public abstract class Collectible extends Drawable implements Collidable, Usable, Droppable {
    protected BonusType bonusType;
    protected int bonusSize;
    protected boolean used = false;

    protected IntCoordinate position = IntCoordinate.getZeroPosition();
    private Blinking blinking = new Blinking(position);

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
        blinking.destroy();
    }

    @Override
    public void drop(@NotNull IntCoordinate position) {
        this.position = new IntCoordinate(position);
        blinking = new Blinking(getShapeCenter());

        CollideManager.register(this);
        Drawable.register(this);
    }

    @Override
    public IntCoordinate getPosition() {
        return position;
    }

    @Override
    public void setPosition(IntCoordinate coordinate) {
        position = new IntCoordinate(coordinate);
        blinking.setPosition(getShapeCenter());
    }

    public Color getColor() {
        return drawableDescriptor.getColor();
    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        drawableDescriptor.setPosition(position);
        super.draw(graphics, camera);
    }

    @Override
    public void renderInInventory(Graphics2D graphics, int x, int y, int width, int height) {
        int gap = Math.max(width / 10, height / 10);

        graphics.setColor(getColor());
        graphics.fillRect(x + gap, y + gap, width - 2 * gap, height - 2 * gap);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(x + gap, y + gap, width - 2 * gap, height - 2 * gap);

        TextLayout bonusTL = new TextLayout(
                String.format("%d", this.bonusSize),
                new Font(Font.SANS_SERIF, Font.BOLD, 25),
                graphics.getFontRenderContext()
        );

        UIManager.drawCenteredText(graphics, Integer.toString(bonusSize),
                x + width / 2, y + height / 2,
                UIManager.THIRDARY_TEXT_FONT,
                Color.WHITE
        );
    }

    public IntCoordinate getShapeCenter() {
        Shape s = getShapeAtPosition();
        return new IntCoordinate((int) s.getBounds().getCenterX(), (int) s.getBounds().getCenterY());
    }

    @Override
    public Shape getShape() {
        return new Rectangle(-3, -3, 16, 16);
    }

}
