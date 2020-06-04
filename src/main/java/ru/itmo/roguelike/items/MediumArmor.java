package ru.itmo.roguelike.items;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.inventory.Usable;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.utils.FileUtils;

import java.awt.*;

public class MediumArmor extends Armor {
    private static Image image = FileUtils.loadImage("pic/med_armr.png");
    private static final int RESIST = 40;

    {
        bonusSize = RESIST;
    }

    public static int getBonusSize() {
        return RESIST;
    }

    @Override
    public void use(Actor actor) {
        super.use(actor, "Medium armor");
    }

    @Override
    public void renderInInventory(Graphics2D graphics, int x, int y, int width, int height) {
        Usable.renderImageInInventory(graphics, x, y, width, height, image);
    }
}
