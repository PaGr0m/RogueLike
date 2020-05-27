package ru.itmo.roguelike.items;

import ru.itmo.roguelike.render.Camera;

import java.awt.*;

import static ru.itmo.roguelike.items.BonusType.HP;

public class MedKitBig extends Collectible {
    BonusType bonusType = HP;
    int bonusSize = 75;

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

}
