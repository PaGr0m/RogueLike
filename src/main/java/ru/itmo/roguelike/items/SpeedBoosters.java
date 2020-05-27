package ru.itmo.roguelike.items;

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

    //FIXME: Check method
    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        drawableDescriptor.setX(this.getPosition().getX()).setY(this.getPosition().getY());
//        super.draw(graphics);
    }

}
