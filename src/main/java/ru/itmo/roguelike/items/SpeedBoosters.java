package ru.itmo.roguelike.items;

import java.awt.*;

import static ru.itmo.roguelike.items.BonusType.SPEED;

/**
 * Add +25 SPEED
 */
public class SpeedBoosters extends Collectible{
    BonusType bonusType = SPEED;
    int bonusSize = 25;

    {
        drawableDescriptor.setColor(color);
        width = 10;
        height = 10;
        color = Color.RED;
    }


    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY);
    }
}
