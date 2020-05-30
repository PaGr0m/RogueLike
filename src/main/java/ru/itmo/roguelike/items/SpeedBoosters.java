package ru.itmo.roguelike.items;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.Camera;

import java.awt.*;

import static ru.itmo.roguelike.items.BonusType.SPEED;

public class SpeedBoosters extends Collectible {
    BonusType bonusType = SPEED;
    int bonusSize = 25;

    {
        drawableDescriptor.setColor(color);
        width = 10;
        height = 10;
        color = Color.RED;
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
