package ru.itmo.roguelike.items;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.Camera;

import java.awt.*;

import static ru.itmo.roguelike.items.BonusType.SPEED;

public class SpeedBoosters extends Collectible {
    {
        width = 10;
        height = 10;
        bonusType = SPEED;
        bonusSize = 25;
        color = Color.GREEN;
        drawableDescriptor.setColor(color);
    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        drawableDescriptor.setPosition(this.getPosition());
//        super.draw(graphics);
    }

    /**
     * @deprecated Not implemented yet
     */
    @Deprecated
    @Override
    public void use(@NotNull Actor actor) {

    }
}
