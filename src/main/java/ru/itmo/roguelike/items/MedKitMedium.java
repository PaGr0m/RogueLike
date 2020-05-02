package ru.itmo.roguelike.items;

import java.awt.*;

import static ru.itmo.roguelike.items.BonusType.HP;

public class MedKitMedium extends Collectible{
    BonusType bonusType = HP;
    int bonusSize = 50;
    private final int width = 10;
    private final int height = 10;
    private final Color color = Color.RED;

    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY);
    }
}
