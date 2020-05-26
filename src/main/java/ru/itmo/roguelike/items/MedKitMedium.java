package ru.itmo.roguelike.items;

import java.awt.*;

import static ru.itmo.roguelike.items.BonusType.HP;

/**
 * Add + 50 HP
 */
public class MedKitMedium extends Collectible {
    BonusType bonusType = HP;
    int bonusSize = 50;

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
