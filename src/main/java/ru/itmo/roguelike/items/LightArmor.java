package ru.itmo.roguelike.items;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.inventory.Usable;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.utils.FileUtils;
import ru.itmo.roguelike.utils.FuncUtils;

import java.awt.*;

public class LightArmor extends Armor {
    private static final Image image = FileUtils.loadImage("pic/light_armr.png");
    private static final int RESIST = 15;

    {
        bonusSize = RESIST;
    }

    public static int getBonusSize() {
        return RESIST;
    }

    @Override
    public void use(Actor actor) {
        super.use(actor, "Light armor");
    }

    @Override
    public void renderInInventory(Graphics2D graphics, int x, int y, int width, int height) {
        FuncUtils.renderImage(graphics, x, y, width, height, image);
    }
}
