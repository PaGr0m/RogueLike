package ru.itmo.roguelike.items;

import ru.itmo.roguelike.render.drawable.DrawableDescriptor;

import java.awt.*;

import static ru.itmo.roguelike.items.BonusType.HP;

public class MedKitBig extends Collectible{
    BonusType bonusType = HP;
    int bonusSize = 75;
    private final int width = 10;
    private final int height = 10;
    private final Color color = Color.RED;

    {
        drawableDescriptor.setColor(color);
    }

    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY);
    }
}
