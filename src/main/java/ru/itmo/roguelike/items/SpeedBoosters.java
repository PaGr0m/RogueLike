package ru.itmo.roguelike.items;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.Camera;

import java.awt.*;

import static ru.itmo.roguelike.items.BonusType.SPEED;

public class SpeedBoosters extends Collectible {
    {
        bonusType = SPEED;
        bonusSize = 25;
        drawableDescriptor.setColor(Color.DARK_GRAY);
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
