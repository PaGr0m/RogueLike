package ru.itmo.roguelike.items;

import java.awt.*;

import static ru.itmo.roguelike.items.BonusType.SPEED;

public class SpeedBoosters extends Collectible{
    BonusType bonusType = SPEED;
    int bonusSize = 25;
    private final int width = 5;
    private final int height = 5;
    private final Color color = Color.RED;

    @Override
    public void draw() {
        drawableDescriptor.setX(this.positionX).setY(this.positionY);
    }
}
