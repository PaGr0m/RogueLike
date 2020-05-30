package ru.itmo.roguelike.items;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.Camera;

import java.awt.*;

import static ru.itmo.roguelike.items.BonusType.HP;

public class MedKitBig extends Collectible {
    BonusType bonusType = HP;
    int bonusSize = 75;

    @Override
    public Color getColor() {
        return Color.pink;
    }

    {
        width = 10;
        height = 10;
    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        drawableDescriptor.setPosition(this.getPosition());
//        super.draw(graphics);
    }

    @Override
    public void use(@NotNull Actor actor) {
        if (!used) {
            used = true;

            actor.heal(bonusSize);
        }
    }
}
